package com.inventory.management.service;

import com.inventory.management.scheduler.JobObject;
import com.inventory.management.vo.dto.StaffPerformanceDto;

import java.util.List;

public interface StaffPerformanceService {

    void updateStaffInformation(JobObject jobObject);
    List<StaffPerformanceDto> getAllStaffPerformance();
}
