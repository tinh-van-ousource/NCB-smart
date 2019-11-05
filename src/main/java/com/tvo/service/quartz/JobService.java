package com.tvo.service.quartz;

import org.springframework.scheduling.quartz.QuartzJobBean;

public interface JobService {

    boolean isJobWithNamePresent(String jobName, String groupKey);

    boolean scheduleCronJob(Class<? extends QuartzJobBean> jobClass, String jobName, String groupKey, String cronExpression);

    boolean updateCronJob(String jobName, String cronExpression);

}
