package com.inventory.management.service.impl;

import com.inventory.management.domain.*;
import com.inventory.management.mapper.*;
import com.inventory.management.operation.access.AccessOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.core.purchaseOrder.approve.ApprovePurchaseOrderRequestOperation;
import com.inventory.management.operation.core.purchaseOrder.create.CreatePurchaseOrderOperation;
import com.inventory.management.operation.core.purchaseOrder.delete.DeletePurchaseOrderOperation;
import com.inventory.management.operation.core.purchaseOrder.list.ListPurchaseOrderAuditOperation;
import com.inventory.management.operation.core.purchaseOrder.list.ListPurchaseOrderOperation;
import com.inventory.management.operation.core.purchaseOrder.list.ListPurchaseOrderRequestOperation;
import com.inventory.management.operation.core.purchaseOrder.reject.RejectPurchaseOrderRequestOperation;
import com.inventory.management.operation.core.purchaseOrder.view.ViewPurchaseOrderAuditOperation;
import com.inventory.management.operation.core.purchaseOrder.view.ViewPurchaseOrderOperation;
import com.inventory.management.operation.core.purchaseOrder.view.ViewPurchaseOrderRequestOperation;
import com.inventory.management.operation.core.purchaseOrderItem.list.ListPurchaseOrderItemOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.operation.delete.DeleteOperationRequest;
import com.inventory.management.operation.delete.DeleteOperationResponse;
import com.inventory.management.operation.list.ListAuditOperationRequest;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.operation.view.ViewOperationRequest;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.service.PurchaseOrderService;
import com.inventory.management.validation.PurchaseOrderRequestValidator;
import com.inventory.management.vo.dto.*;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final ViewPurchaseOrderOperation viewPurchaseOrderOperation;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;
    private final ListPurchaseOrderOperation listPurchaseOrderOperation;
    private final ListPurchaseOrderItemOperation listPurchaseOrderItemOperation;
    private final ListPurchaseOrderAuditOperation listPurchaseOrderAuditsOperation;
    private final ViewPurchaseOrderAuditOperation viewPurchaseOrderAuditOperation;
    private final PurchaseOrderAuditMapper purchaseOrderAuditResponseMapper;
    private final PurchaseOrderRequestMapper purchaseOrderRequestMapper;
    private final ViewPurchaseOrderRequestOperation viewPurchaseOrderRequestOperation;
    private final ListPurchaseOrderRequestOperation listPurchaseOrderRequestsOperation;
    private final CreatePurchaseOrderOperation createPurchaseOrderOperation;
    private final DeletePurchaseOrderOperation deletePurchaseOrderOperation;
    private final ApprovePurchaseOrderRequestOperation approvePurchaseOrderRequestOperation;
    private final RejectPurchaseOrderRequestOperation rejectPurchaseOrderRequestOperation;
    private final ProductRepository productRepository;
    private final PurchaseOrderRequestValidator purchaseOrderRequestValidator;
    private final ProductMapper productMapper;

    @Override
    public Page<PurchaseOrderDto> getPurchaseOrders(PageRequest pageRequest, Map<String, Object> purchaseOrderFilter) {
        return listPurchaseOrderOperation.process(new ListOperationRequest(pageRequest, purchaseOrderFilter))
                .getPage()
                .map(purchaseOrderMapper::toDto);
    }

    @Override
    public Page<PurchaseOrderItemDto> getPurchaseOrderItems(PageRequest pageRequest, Map<String, Object> purchaseOrderItemFilter) {
        return listPurchaseOrderItemOperation.process(new ListOperationRequest(pageRequest, purchaseOrderItemFilter))
                .getPage()
                .map(purchaseOrderItemMapper::toDto);
    }

    @Override
    public PurchaseOrderDto getPurchaseOrder(Long id) {
        return viewPurchaseOrderOperation.process(new ViewOperationRequest<>(id))
                .getEntity().map(purchaseOrderMapper::toDto)
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
    }

    @Override
    public PurchaseOrderDto deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = viewPurchaseOrderOperation.process(new ViewOperationRequest<>(id)).getEntity()
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
        BeanUtils.copyProperties(purchaseOrder, purchaseOrderRequest);
        purchaseOrderRequest.setPurchaseOrderId(purchaseOrder.getId());
        purchaseOrderRequest.setItems(new ArrayList<>());
        DeleteOperationRequest<PurchaseOrder, PurchaseOrderRequest> request = new DeleteOperationRequest<>(purchaseOrder, purchaseOrderRequest);
        DeleteOperationResponse<PurchaseOrder> operationResponse = deletePurchaseOrderOperation.process(request);
        return purchaseOrderMapper.toDto(operationResponse.getDomain());
    }

    @Override
    public PurchaseOrderAuditDto getPurchaseOrderAudit(Long id, Integer revisionId) {
        return viewPurchaseOrderAuditOperation.process(new ViewAuditOperationRequest<>(id, revisionId))
                .getAuditedEntity()
                .map(purchaseOrderAuditResponseMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error has occurred"));
    }

    @Override
    public Page<PurchaseOrderAuditDto> getPurchaseOrderAudits(Long id, PageRequest pageRequest, Map<String, Object> purchaseOrderAuditsFilter) {
        return listPurchaseOrderAuditsOperation.process(new ListAuditOperationRequest<>(pageRequest, purchaseOrderAuditsFilter, id))
                .getPage().map(purchaseOrderAuditResponseMapper::toDto);
    }

    @Override
    public Page<PurchaseOrderRequestDto> getPurchaseOrderRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter) {
        return listPurchaseOrderRequestsOperation
                .process(new ListOperationRequest(pageRequest, currencyRequestFilter))
                .getPage().map(purchaseOrderRequestMapper::toDto);
    }

    @Override
    public PurchaseOrderRequestDto getPurchaseOrderRequest(Long requestId) {
        return viewPurchaseOrderRequestOperation.process(new ViewOperationRequest<>(requestId))
                .getEntity().map(purchaseOrderRequestMapper::toDto).orElseThrow(() -> new CustomApiException("not found"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long postPurchaseOrderRequest(PurchaseOrderRequestDto purchaseOrderRequestBody) {
        CreateOperationRequest<PurchaseOrderRequest> request = new CreateOperationRequest<>(purchaseOrderRequestMapper.toEntity(purchaseOrderRequestBody));
        CreateOperationResponse<PurchaseOrderRequest> operationResponse = createPurchaseOrderOperation.process(request);
        return operationResponse.getRequest().getRequestId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PurchaseOrderRequestDto approvePurchaseOrderRequest(Long requestId) {
        return purchaseOrderRequestMapper.toDto(approvePurchaseOrderRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public PurchaseOrderRequestDto rejectPurchaseOrderRequest(Long requestId) {
        return purchaseOrderRequestMapper.toDto(rejectPurchaseOrderRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public List<PurchaseOrderItemDto> populateItems(List<PurchaseOrderItemDto> items) {
        purchaseOrderRequestValidator.validateOrderItems(items);
        List<PurchaseOrderItemDto> orderItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(items)) {
            orderItemList = items.stream().peek(this::getTotalAmount).collect(Collectors.toList());
        }
        return orderItemList;
    }

    private void getTotalAmount(PurchaseOrderItemDto item) {
        Product product = productRepository.findByCode(item.getProductCode()).orElseThrow();
        item.setProductName(product.getName());
        item.setAmount(product.getUnitPrice());
        BigDecimal totalAmount = item.getAmount().multiply( new BigDecimal(item.getQuantity()));
        if(!item.getVatEnabled()) item.setTotalAmount(totalAmount);
        else if(product.getTaxInPercentage() != 0) {
            BigDecimal vatAmount = BigDecimal.valueOf((totalAmount.doubleValue() * product.getTaxInPercentage()) / 100);
            item.setTotalAmount(totalAmount.add(vatAmount));
        }else item.setTotalAmount(totalAmount);
    }

    @Override
    public List<ProductDto> getProducts() {
        return this.productRepository.findAllActiveStatus().stream().map(productMapper::toDto).collect(Collectors.toList());
    }


}
