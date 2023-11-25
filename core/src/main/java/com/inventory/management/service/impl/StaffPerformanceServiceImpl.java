package com.inventory.management.service.impl;

import com.inventory.management.domain.StaffPerformance;
import com.inventory.management.mapper.StaffPerformanceMapper;
import com.inventory.management.repository.PurchaseOrderRepository;
import com.inventory.management.repository.StaffPerformanceRepository;
import com.inventory.management.scheduler.JobObject;
import com.inventory.management.service.StaffPerformanceService;
import com.inventory.management.vo.dto.StaffPerformanceDto;
import com.inventory.management.vo.dto.performance.PerformanceOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings({"Duplicates"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StaffPerformanceServiceImpl implements StaffPerformanceService {


    private final PurchaseOrderRepository purchaseOrderRepository;
    private final StaffPerformanceRepository staffPerformanceRepository;
    private final StaffPerformanceMapper staffPerformanceMapper;

    @Override
    public void updateStaffInformation(JobObject jobObject) {
        if (jobObject.getUpdateType().equals("inline")) handleInlineUpdate(jobObject);
        if (jobObject.getUpdateType().equals("aggregate")) handleAggregateUpdate(jobObject);
    }

    @Override
    public List<StaffPerformanceDto> getAllStaffPerformance() {
        return staffPerformanceRepository.findAll().stream().map(staffPerformanceMapper::toDto).collect(Collectors.toList());
    }

    void handleInlineUpdate(JobObject jobObject) {
        List<PerformanceOrder> orders = purchaseOrderRepository.findAllPerformanceOrder();
        if (!CollectionUtils.isEmpty(orders)) {
            staffPerformanceRepository.deleteAll();
            List<StaffPerformance> performances = buildPerformanceModel(orders, jobObject);
            staffPerformanceRepository.saveAll(performances);
        }
    }

    void handleAggregateUpdate(JobObject jobObject) {
        Optional<Instant> optionalInstant = staffPerformanceRepository.getLastTime().stream().findFirst();
        List<PerformanceOrder> orders = optionalInstant.isEmpty() ? purchaseOrderRepository.findAllPerformanceOrder() :
                purchaseOrderRepository.findAllPerformanceOrder(optionalInstant.get());
        if (!CollectionUtils.isEmpty(orders)) {
            staffPerformanceRepository.updateNewStatus(false);
            List<StaffPerformance> performances = buildPerformanceModel(orders, jobObject);
            staffPerformanceRepository.saveAll(performances);
        }
    }

    private List<StaffPerformance> buildPerformanceModel(List<PerformanceOrder> rawPerformances, JobObject jobObject) {
        if (!CollectionUtils.isEmpty(rawPerformances)) {
            double minimum = rawPerformances.stream().map(PerformanceOrder::getAmount).min(Double::compareTo).orElseThrow();
            double maximum = rawPerformances.stream().map(PerformanceOrder::getAmount).max(Double::compareTo).orElseThrow();
            return rawPerformances.stream().map(rawPerformance -> {
                Long count = rawPerformance.getCounter();
                Double amount = rawPerformance.getAmount();
                StaffPerformance model = new StaffPerformance();
                model.setUsername(rawPerformance.getUser());
                model.setBonusPoint(count * jobObject.getBonusPoint());
                //using min-max scaling
                double performanceValue = (maximum - minimum) == 0d ? (Math.max(maximum, minimum) > 0 ? 1d : 0d) : ((amount - minimum) / (maximum - minimum));
                double performancePercent = performanceValue * 100;
                model.setAveragePeriodicPerformance(performancePercent);
                model.setStatus(getStatus(performancePercent));
                model.setIsNew(true);
                model.setType(jobObject.getUpdateType());
                model.setPeriod(jobObject.getPeriod());
                model.setVersion(UUID.randomUUID().toString());
                return model;
            }).collect(Collectors.toList());
        }
        return List.of();
    }

    private String getStatus(double percentage) {
        String poor = "Poor";
        String average = "Average";
        String excellent = "Excellent";
        String result = poor;
        if (percentage > 25d && percentage < 75d) {
            result = average;
        } else if (percentage > 75d) {
            result = excellent;
        }
        return result;
    }
}
