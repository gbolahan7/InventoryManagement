package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.repository.PerformanceSettingRepository;
import com.inventory.management.scheduler.SchedulerPeriod;
import com.inventory.management.util.Constant;
import com.inventory.management.vo.problem.ValidationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Validate
@AllArgsConstructor
public class PerformanceSettingRequestValidator implements Validator<String, PerformanceSettingRequest> {

    final private PerformanceSettingRepository performanceSettingRepository;

    private final Map<String, Consumer<PerformanceSettingRequest>> validators = registerValidators();

    private static void buildBasicValidation(PerformanceSettingRequest request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.performance.setting.empty");
        else {
            if (!StringUtils.hasText(request.getName()))
                builder.addError("validation.error.performance.setting.name.empty");
        }
    }

    @Override
    public Map<String, Consumer<PerformanceSettingRequest>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<PerformanceSettingRequest>> registerValidators() {
        Map<String, Consumer<PerformanceSettingRequest>> validators = new HashMap<>();
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<PerformanceSettingRequest> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (performanceSettingRepository.findById(request.getPerformanceSettingId()).isEmpty())
                    builder.addError("validation.error.performanceSetting.request");
                if ((Objects.nonNull(request.getStaffStartTime()) && !StringUtils.hasText(request.getStaffPeriod())) )
                    builder.addError("validation.error.performance.setting.request.both.staff.time.period");
                else if (StringUtils.hasText(request.getStaffPeriod()) && Objects.isNull(request.getStaffStartTime()))
                    builder.addError("validation.error.performance.setting.request.both.staff.time.period");
                else if (Objects.isNull(request.getStaffStartTime()) && !StringUtils.hasText(request.getStaffPeriod()))
                    request.setStaffStopTime(null);
                if(Objects.nonNull(request.getStaffStartTime()) && StringUtils.hasText(request.getStaffPeriod()) &&
                        !StringUtils.hasText(request.getStaffUpdateType()) )
                    builder.addError("validation.error.performance.setting.request.staff.type.update");
                if(StringUtils.hasText(request.getStaffUpdateType()) && (!request.getStaffUpdateType().equals("inline") &&
                        !request.getStaffUpdateType().equals("aggregate")) )
                    builder.addError("validation.error.performance.setting.request.staff.type.update.inline.aggregate");
                if(Objects.nonNull(request.getStaffStartTime()) && StringUtils.hasText(request.getStaffPeriod()) &&
                        Objects.isNull(request.getStaffBonusPoint()) )
                    builder.addError("validation.error.performance.setting.request.staff.type.bonus.point");
                try{
                    SchedulerPeriod.valueOf(request.getStaffPeriod());
                }catch (IllegalArgumentException ex){
                    builder.addError("validation.error.performance.setting.request.staff.period");
                }
                handleValidatorError(builder.build());
            }
        };
    }

}
