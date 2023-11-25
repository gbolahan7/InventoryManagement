package com.inventory.management.operation.core.performanceSetting.approve;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.PerformanceSettingRepository;
import com.inventory.management.repository.PerformanceSettingRequestRepository;
import com.inventory.management.scheduler.PerformanceJobDelegator;
import com.inventory.management.validation.PerformanceSettingRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.inventory.management.util.Constant.APPROVED;
import static com.inventory.management.util.Constant.MODIFY;

@Operation
public class ApprovePerformanceSettingRequestOperation extends AccessOperation<PerformanceSettingRequest, Long, PerformanceSettingRequestRepository> {

    private final PerformanceSettingRepository performanceSettingRepository;
    private final PerformanceSettingRequestValidator performanceSettingRequestValidator;
    private final PerformanceJobDelegator jobDelegator;
    private final Map<String, Function<PerformanceSettingRequest, PerformanceSetting>> handlers = getApproveHandlers();

    public ApprovePerformanceSettingRequestOperation(PerformanceSettingRequestRepository performanceSettingRequestRepository,
                                                     PerformanceSettingRepository performanceSettingRepository,
                                                     PerformanceSettingRequestValidator performanceSettingRequestValidator,
                                                     PerformanceJobDelegator jobDelegator) {
        super(performanceSettingRequestRepository);
        this.performanceSettingRepository = performanceSettingRepository;
        this.performanceSettingRequestValidator = performanceSettingRequestValidator;
        this.jobDelegator = jobDelegator;
    }

    @Override
    protected PerformanceSettingRequest operate(PerformanceSettingRequest requestEntity) {
        PerformanceSetting performanceSettingEntity = handlers.get(requestEntity.getRequestType()).apply(requestEntity);
        PerformanceSetting setting = performanceSettingRepository.save(performanceSettingEntity);
        jobDelegator.handlePerformanceScheduleCreate(setting);
        requestEntity.setRequestStatus(APPROVED);
        return repository.save(requestEntity);
    }

    private PerformanceSetting handleModify(PerformanceSettingRequest request) {
        performanceSettingRequestValidator.validate(MODIFY, request);
        PerformanceSetting performanceSetting = performanceSettingRepository.findById(request.getPerformanceSettingId())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        performanceSetting.setStaffPeriod(request.getStaffPeriod());
        performanceSetting.setStaffStopTime(request.getStaffStopTime());
        performanceSetting.setStaffStartTime(request.getStaffStartTime());
        performanceSetting.setStaffBonusPoint(request.getStaffBonusPoint());
        performanceSetting.setStaffUpdateType(request.getStaffUpdateType());
        performanceSetting.setVersion(UUID.randomUUID().toString());
        return performanceSetting;
    }

    private Map<String, Function<PerformanceSettingRequest, PerformanceSetting>> getApproveHandlers() {
        Map<String, Function<PerformanceSettingRequest, PerformanceSetting>> approveHandlers = new HashMap<>();
        approveHandlers.put(MODIFY, this::handleModify);
        return approveHandlers;
    }
}
