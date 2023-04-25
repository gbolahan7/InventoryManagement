package com.inventory.management.operation.core.purchaseOrder.approve;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.PurchaseOrderRepository;
import com.inventory.management.repository.PurchaseOrderRequestRepository;
import com.inventory.management.validation.PurchaseOrderRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.inventory.management.util.Constant.*;

@Operation
public class ApprovePurchaseOrderRequestOperation extends AccessOperation<PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderRequestValidator purchaseOrderRequestValidator;
    private final ProductRepository productRepository;
    private final Map<String, Function<PurchaseOrderRequest, PurchaseOrder>> handlers = getApproveHandlers();

    public ApprovePurchaseOrderRequestOperation(PurchaseOrderRequestRepository purchaseOrderRequestRepository,
                                                PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderRequestValidator purchaseOrderRequestValidator,
                                                ProductRepository productRepository) {
        super(purchaseOrderRequestRepository);
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderRequestValidator = purchaseOrderRequestValidator;
        this.productRepository = productRepository;
    }

    @Override
    protected PurchaseOrderRequest operate(PurchaseOrderRequest requestEntity) {
        PurchaseOrder purchaseOrderEntity = handlers.get(requestEntity.getRequestType()).apply(requestEntity);
        purchaseOrderRepository.save(purchaseOrderEntity);
        requestEntity.setRequestStatus(APPROVED);
        return repository.save(requestEntity);
    }

    private PurchaseOrder handleCreate(PurchaseOrderRequest request) {
        purchaseOrderRequestValidator.validate(CREATE, request);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchasedDate(request.getPurchasedDate());
        purchaseOrder.setDescription(request.getDescription());
        purchaseOrder.setStatus(request.getStatus());
        purchaseOrder.setItems(mapItems(request.getItems()));
        purchaseOrder.setVersion(UUID.randomUUID().toString());
        adjustProduct(purchaseOrder.getItems(), purchaseOrder);
        return purchaseOrder;
    }

    private PurchaseOrder handleModify(PurchaseOrderRequest request) {
        purchaseOrderRequestValidator.validate(MODIFY, request);
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        purchaseOrder.setStatus(request.getStatus());
        purchaseOrder.setVersion(UUID.randomUUID().toString());
        return purchaseOrder;
    }

    private Map<String, Function<PurchaseOrderRequest, PurchaseOrder>> getApproveHandlers() {
        Map<String, Function<PurchaseOrderRequest, PurchaseOrder>> approveHandlers = new HashMap<>();
        approveHandlers.put(CREATE, this::handleCreate);
        approveHandlers.put(MODIFY, this::handleModify);
        return approveHandlers;
    }

    private List<PurchaseOrderItem> mapItems(List<PurchaseOrderItem> requests) {
        List<PurchaseOrderItem> items = new ArrayList<>();
        if(!CollectionUtils.isEmpty(requests)) items = requests.stream().map(this::mapItems).collect(Collectors.toList());
        return items;
    }

    private PurchaseOrderItem mapItems(PurchaseOrderItem request) {
        PurchaseOrderItem newItem = new PurchaseOrderItem();
        BeanUtils.copyProperties(request, newItem);
        newItem.setId(null);
        return newItem;
    }

    private void adjustProduct(List<PurchaseOrderItem> items, PurchaseOrder purchaseOrder) {
        if(!CollectionUtils.isEmpty(items)) {
            for(PurchaseOrderItem item: items) {
                item.setPurchaseOrder(purchaseOrder);
                item.setPurchaseOrderRequest(null);
                productRepository.findByCode(item.getProductCode()).ifPresent(product -> {
                    Long value = Math.max(0, product.getQuantity() - item.getQuantity());
                    productRepository.updateProductAmount(item.getProductCode(), value);
                });
            }
        }
    }
}
