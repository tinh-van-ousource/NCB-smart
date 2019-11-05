package com.tvo.service.quartz;

import com.tvo.common.DateTimeUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    @Lazy
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ApplicationContext context;

    @Override
    public boolean isJobWithNamePresent(String jobName, String groupKey) {
        try {
            JobKey jobKey = new JobKey(jobName, groupKey);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler.checkExists(jobKey)){
                return true;
            }
        } catch (SchedulerException e) {
            logger.error("SchedulerException while checking job with name and group exist:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean scheduleCronJob(Class<? extends QuartzJobBean> jobClass, String jobName, String groupKey, String cronExpression) {
        String jobKey = jobName;
        String triggerKey = jobName;

        JobDetail jobDetail = JobUtil.createJob(jobClass, false, context, jobKey, groupKey);
        Trigger cronTriggerBean = JobUtil.createCronTrigger(triggerKey, DateTimeUtil.getNow(), cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
            logger.info("Job with key jobKey :  " + jobKey +  " and group :" + groupKey + " scheduled successfully for date :" + dt);
            return true;
        } catch (SchedulerException e) {
            logger.error("SchedulerException while scheduling job with key :" + jobKey + " message :" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateCronJob(String jobName, String cronExpression) {
        String jobKey = jobName;
        try {
            Trigger newTrigger = JobUtil.createCronTrigger(jobKey, DateTimeUtil.getNow(), cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

            Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);
            logger.info("Trigger associated with jobKey :" + jobKey + " rescheduled successfully for date :" + dt);
            return true;
        } catch ( Exception e ) {
            logger.error("SchedulerException while updating cron job with key :" + jobKey + " message :" + e.getMessage());
            return false;
        }
    }
}
