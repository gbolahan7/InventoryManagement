package com.inventory.management.service.impl;

import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.mapper.PerformanceSettingAuditMapper;
import com.inventory.management.mapper.PerformanceSettingMapper;
import com.inventory.management.mapper.PerformanceSettingRequestMapper;
import com.inventory.management.operation.access.AccessOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.core.performanceSetting.approve.ApprovePerformanceSettingRequestOperation;
import com.inventory.management.operation.core.performanceSetting.create.CreatePerformanceSettingOperation;
import com.inventory.management.operation.core.performanceSetting.list.ListPerformanceSettingAuditOperation;
import com.inventory.management.operation.core.performanceSetting.list.ListPerformanceSettingRequestOperation;
import com.inventory.management.operation.core.performanceSetting.reject.RejectPerformanceSettingRequestOperation;
import com.inventory.management.operation.core.performanceSetting.view.ViewPerformanceSettingAuditOperation;
import com.inventory.management.operation.core.performanceSetting.view.ViewPerformanceSettingOperation;
import com.inventory.management.operation.core.performanceSetting.view.ViewPerformanceSettingRequestOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.operation.list.ListAuditOperationRequest;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.operation.view.ViewOperationRequest;
import com.inventory.management.service.PerformanceSettingService;
import com.inventory.management.vo.dto.PerformanceSettingAuditDto;
import com.inventory.management.vo.dto.PerformanceSettingDto;
import com.inventory.management.vo.dto.PerformanceSettingRequestDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class PerformanceSettingServiceImpl implements PerformanceSettingService {

    private final ViewPerformanceSettingOperation viewPerformanceSettingOperation;
    private final PerformanceSettingMapper performanceSettingMapper;
    private final ListPerformanceSettingAuditOperation listPerformanceSettingAuditsOperation;
    private final ViewPerformanceSettingAuditOperation viewPerformanceSettingAuditOperation;
    private final PerformanceSettingAuditMapper performanceSettingAuditResponseMapper;
    private final PerformanceSettingRequestMapper performanceSettingRequestMapper;
    private final ViewPerformanceSettingRequestOperation viewPerformanceSettingRequestOperation;
    private final ListPerformanceSettingRequestOperation listPerformanceSettingRequestsOperation;
    private final CreatePerformanceSettingOperation createPerformanceSettingOperation;
    private final ApprovePerformanceSettingRequestOperation approvePerformanceSettingRequestOperation;
    private final RejectPerformanceSettingRequestOperation rejectPerformanceSettingRequestOperation;

    @Override
    public PerformanceSettingDto getPerformanceSetting(Long id) {
        return viewPerformanceSettingOperation.process(new ViewOperationRequest<>(id))
                .getEntity().map(performanceSettingMapper::toDto)
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
    }

    @Override
    public PerformanceSettingAuditDto getPerformanceSettingAudit(Long id, Integer revisionId) {
        return viewPerformanceSettingAuditOperation.process(new ViewAuditOperationRequest<>(id, revisionId))
                .getAuditedEntity()
                .map(performanceSettingAuditResponseMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error has occurred"));
    }

    @Override
    public Page<PerformanceSettingAuditDto> getPerformanceSettingAudits(Long id, PageRequest pageRequest, Map<String, Object> performanceSettingAuditsFilter) {
        return listPerformanceSettingAuditsOperation.process(new ListAuditOperationRequest<>(pageRequest, performanceSettingAuditsFilter, id))
                .getPage().map(performanceSettingAuditResponseMapper::toDto);
    }

    @Override
    public Page<PerformanceSettingRequestDto> getPerformanceSettingRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter) {
        return listPerformanceSettingRequestsOperation
                .process(new ListOperationRequest(pageRequest, currencyRequestFilter))
                .getPage().map(performanceSettingRequestMapper::toDto);
    }

    @Override
    public PerformanceSettingRequestDto getPerformanceSettingRequest(Long requestId) {
        return viewPerformanceSettingRequestOperation.process(new ViewOperationRequest<>(requestId))
                .getEntity().map(performanceSettingRequestMapper::toDto).orElseThrow(() -> new CustomApiException("not found"));
    }

    @Override
    public Long postPerformanceSettingRequest(PerformanceSettingRequestDto performanceSettingRequestBody) {
        CreateOperationRequest<PerformanceSettingRequest> request = new CreateOperationRequest<>(performanceSettingRequestMapper.toEntity(performanceSettingRequestBody));
        CreateOperationResponse<PerformanceSettingRequest> operationResponse = createPerformanceSettingOperation.process(request);
        return operationResponse.getRequest().getRequestId();
    }

    @Override
    public PerformanceSettingRequestDto approvePerformanceSettingRequest(Long requestId) {
        return performanceSettingRequestMapper.toDto(approvePerformanceSettingRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public PerformanceSettingRequestDto rejectPerformanceSettingRequest(Long requestId) {
        return performanceSettingRequestMapper.toDto(rejectPerformanceSettingRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }


}
