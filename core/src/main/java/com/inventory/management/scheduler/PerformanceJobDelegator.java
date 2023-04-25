package com.inventory.management.scheduler;


import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.vo.problem.CustomApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import static org.quartz.TriggerBuilder.newTrigger;

@AllArgsConstructor
@Component
@Slf4j
public class PerformanceJobDelegator {

    public static final String STAFF_PERFORMANCE_KEY = "staff_performance";
    public static final String JOB_DATA = "job_data";
    private static final Map<String, Class<? extends Job>> jobMetadata = Map.of(
            STAFF_PERFORMANCE_KEY, StaffPerformanceJob.class
    );

    private final Scheduler scheduler;

    public void handlePerformanceScheduleCreate(PerformanceSetting performanceSetting) {
        List<JobObject> jobs = getAllPerformanceJob(performanceSetting);
        for (JobObject job : jobs) {
            log.info("Deleting jobs for {}", job.getGroupKey());
            deletePerformanceJobs(job);
            log.info("Creating new jobs for {}", job.getGroupKey());
            createPerformanceScheduleJob(job);
        }
    }

    private void deletePerformanceJobs(final JobObject jobObject) {
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(jobObject.getGroupKey()));
            scheduler.deleteJobs(new ArrayList<>(jobKeys));
        } catch (SchedulerException e) {
            throw new CustomApiException(e.getMessage());
        }
    }

    private void createPerformanceScheduleJob(final JobObject jobObject) {
        try {
            JobDetail jobDetails = getJobBuilder(jobObject.getGroupKey(), buildTimeIdentity(jobObject))
                    .usingJobData(performanceJobData(jobObject))
                    .build();
            Trigger trigger = getTrigger(buildTimeIdentity(jobObject), jobObject.getGroupKey(), jobObject);
            scheduler.scheduleJob(jobDetails, trigger);
        } catch (SchedulerException e) {
            throw new CustomApiException(e.getMessage());
        }
    }

    private String buildTimeIdentity(JobObject jobObject) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(jobObject.getStart(), ZoneId.systemDefault());
        return String.format("%s_%s_%s_%s_%s_%s_%s", zdt.getSecond(), zdt.getMinute(), zdt.getHour(), zdt.getDayOfWeek(),
                zdt.getMonth(), zdt.getYear(), jobObject.getGroupKey());
    }

    private JobDataMap performanceJobData(JobObject jobObject) {
        JobDataMap data = new JobDataMap();
        data.put(JOB_DATA, jobObject);
        return data;
    }

    private JobBuilder getJobBuilder(String id, String identity) {
        return JobBuilder.newJob(jobMetadata.get(id)).withIdentity(identity, id);
    }


    private Trigger getTrigger(String identity, String id, JobObject object) {
        return newTrigger()
                .withIdentity(identity, id)
                .withSchedule(getCalendarPeriod(SchedulerPeriod.valueOf(object.getPeriod())))
                .startAt(Date.from(object.getStart()))
                .endAt(object.getStop() == null ? null : Date.from(object.getStop()))
                .build();
    }

    CalendarIntervalScheduleBuilder getCalendarPeriod(SchedulerPeriod rawPeriod) {
        CalendarIntervalScheduleBuilder builder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule();
        switch (rawPeriod) {
            case SECOND:
                return builder.withIntervalInSeconds(1);
            case MINUTE:
                return builder.withIntervalInMinutes(1);
            case HOUR:
                return builder.withIntervalInHours(1);
            case DAY:
                return builder.withIntervalInDays(1);
            case WEEK:
                return builder.withIntervalInWeeks(1);
            case MONTH:
                return builder.withIntervalInMonths(1);
            case YEAR:
                return builder.withIntervalInYears(1);
            default:
                return builder;
        }
    }

    private List<JobObject> getAllPerformanceJob(PerformanceSetting setting) {
        List<JobObject> jobs = new ArrayList<>();
        if (StringUtils.hasText(setting.getStaffPeriod()) && setting.getStaffStartTime() != null) {
            JobObject staffJobObject =
                    new JobObject(STAFF_PERFORMANCE_KEY, setting.getStaffPeriod(),
                            setting.getStaffStartTime(), setting.getStaffStopTime(), setting.getStaffUpdateType(),setting.getStaffBonusPoint(), Instant.now());
            jobs.add(staffJobObject);
        }
        return jobs;
    }


}