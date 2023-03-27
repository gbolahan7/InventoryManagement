package com.inventory.management.service.impl;

import com.inventory.management.domain.UnitRequest;
import com.inventory.management.mapper.UnitAuditMapper;
import com.inventory.management.mapper.UnitMapper;
import com.inventory.management.mapper.UnitRequestMapper;
import com.inventory.management.operation.access.AccessOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.core.unit.approve.ApproveUnitRequestOperation;
import com.inventory.management.operation.core.unit.create.CreateUnitOperation;
import com.inventory.management.operation.core.unit.list.ListUnitAuditOperation;
import com.inventory.management.operation.core.unit.list.ListUnitOperation;
import com.inventory.management.operation.core.unit.list.ListUnitRequestOperation;
import com.inventory.management.operation.core.unit.reject.RejectUnitRequestOperation;
import com.inventory.management.operation.core.unit.view.ViewUnitAuditOperation;
import com.inventory.management.operation.core.unit.view.ViewUnitOperation;
import com.inventory.management.operation.core.unit.view.ViewUnitRequestOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.operation.list.ListAuditOperationRequest;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.operation.view.ViewOperationRequest;
import com.inventory.management.service.UnitService;
import com.inventory.management.vo.dto.UnitAuditDto;
import com.inventory.management.vo.dto.UnitDto;
import com.inventory.management.vo.dto.UnitRequestDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final ViewUnitOperation viewUnitOperation;
    private final UnitMapper unitMapper;
    private final ListUnitOperation listUnitOperation;
    private final ListUnitAuditOperation listUnitAuditsOperation;
    private final ViewUnitAuditOperation viewUnitAuditOperation;
    private final UnitAuditMapper unitAuditResponseMapper;
    private final UnitRequestMapper unitRequestMapper;
    private final ViewUnitRequestOperation viewUnitRequestOperation;
    private final ListUnitRequestOperation listUnitRequestsOperation;
    private final CreateUnitOperation createUnitOperation;
    private final ApproveUnitRequestOperation approveUnitRequestOperation;
    private final RejectUnitRequestOperation rejectUnitRequestOperation;

    @Override
    public Page<UnitDto> getUnits(PageRequest pageRequest, Map<String, Object> unitFilter) {
        return listUnitOperation.process(new ListOperationRequest(pageRequest, unitFilter))
                .getPage()
                .map(unitMapper::toDto);
    }

    @Override
    public UnitDto getUnit(Long id) {
        return viewUnitOperation.process(new ViewOperationRequest<>(id))
                .getEntity().map(unitMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error occurred while fetching"));
    }

    @Override
    public UnitAuditDto getUnitAudit(Long id, Integer revisionId) {
        return viewUnitAuditOperation.process(new ViewAuditOperationRequest<>(id, revisionId))
                .getAuditedEntity()
                .map(unitAuditResponseMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error has occurred"));
    }

    @Override
    public Page<UnitAuditDto> getUnitAudits(Long id, PageRequest pageRequest, Map<String, Object> unitAuditsFilter) {
        return listUnitAuditsOperation.process(new ListAuditOperationRequest<>(pageRequest, unitAuditsFilter, id))
                .getPage().map(unitAuditResponseMapper::toDto);
    }

    @Override
    public Page<UnitRequestDto> getUnitRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter) {
        return listUnitRequestsOperation
                .process(new ListOperationRequest(pageRequest, currencyRequestFilter))
                .getPage().map(unitRequestMapper::toDto);
    }

    @Override
    public UnitRequestDto getUnitRequest(Long requestId) {
        return viewUnitRequestOperation.process(new ViewOperationRequest<>(requestId))
                .getEntity().map(unitRequestMapper::toDto).orElseThrow(() -> new CustomApiException("not found"));
    }

    @Override
    public Long postUnitRequest(UnitRequestDto unitRequestBody) {
        CreateOperationRequest<UnitRequest> request = new CreateOperationRequest<>(unitRequestMapper.toEntity(unitRequestBody));
        CreateOperationResponse<UnitRequest> operationResponse = createUnitOperation.process(request);
        return operationResponse.getRequest().getRequestId();
    }

    @Override
    public UnitRequestDto approveUnitRequest(Long requestId) {
        return unitRequestMapper.toDto(approveUnitRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public UnitRequestDto rejectUnitRequest(Long requestId) {
        return unitRequestMapper.toDto(rejectUnitRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }


}