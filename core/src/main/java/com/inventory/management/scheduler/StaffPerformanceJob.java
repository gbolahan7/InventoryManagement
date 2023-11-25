package com.inventory.management.scheduler;

import com.inventory.management.service.StaffPerformanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import static com.inventory.management.scheduler.PerformanceJobDelegator.JOB_DATA;

@Slf4j
@RequiredArgsConstructor
@Component
public class StaffPerformanceJob extends QuartzJobBean {

    private final StaffPerformanceService staffPerformanceService;

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        log.info("Executing Staff Performance Schedule Job with key: {}", jobDetail.getKey());
        try {
            JobDataMap jobData = jobDetail.getJobDataMap();
            JobObject staffPerformanceSchedule = getStaffPerformanceSchedule(jobData);
            staffPerformanceService.updateStaffInformation(staffPerformanceSchedule);
        } catch (Exception e) {
            log.error("StaffPerformanceScheduleJob execution failed, job key:" + jobDetail.getKey(), e);
            context.setResult("failed");
            throw new JobExecutionException(e, true);
        }
        log.info("Staff Performance Schedule Job {} execution is complete", jobDetail.getKey());
    }

    private JobObject getStaffPerformanceSchedule(JobDataMap jobData) {
        return (JobObject) jobData.get(JOB_DATA);
    }

}
