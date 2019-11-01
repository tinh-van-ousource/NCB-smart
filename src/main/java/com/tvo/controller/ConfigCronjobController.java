package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.job.ConfigMbAppJob;
import com.tvo.job.ConfigMbAppJobListener;
import com.tvo.model.ConfigCronjob;
import com.tvo.response.ResponeData;
import com.tvo.service.ConfigCronjobService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/config-cronjob")
public class ConfigCronjobController {

    private static final String JOB_KEY_START_NAME = "JOB_KEY_START_NAME";
    private static final String JOB_KEY_START_GROUP = "JOB_KEY_START_GROUP";
    private static final String JOB_KEY_START_TRIGGER_NAME = "JOB_KEY_START_TRIGGER_NAME";
    private static final String JOB_KEY_START_TRIGGER_GROUP = "JOB_KEY_START_TRIGGER_GROUP";

    private static final String JOB_KEY_END_NAME = "JOB_KEY_END_NAME";
    private static final String JOB_KEY_END_GROUP = "JOB_KEY_END_GROUP";
    private static final String JOB_KEY_END_TRIGGER_NAME = "JOB_KEY_END_TRIGGER_NAME";
    private static final String JOB_KEY_END_TRIGGER_GROUP = "JOB_KEY_END_TRIGGER_GROUP";
    private static final JobKey jobKeyStart = new JobKey(JOB_KEY_START_NAME, JOB_KEY_START_GROUP);
    private static final JobKey jobKeyEnd = new JobKey(JOB_KEY_END_NAME, JOB_KEY_END_GROUP);

    @Autowired
    private ConfigCronjobService configCronjobService;

    @PostMapping("/saveOrUpdate")
    public ResponeData<ConfigCronjob> findTypesByCode(@RequestBody ConfigCronjobRqDto configCronjobRqDto) {
        ConfigCronjob configCronjob = configCronjobService.saveOrUpdate(configCronjobRqDto);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, configCronjob);
    }

    @GetMapping("/cronJob")
    public void configMbAppCronTrigger(@RequestParam String start, @RequestParam String end) throws SchedulerException {
        cronTriggerStart(start);
    }

    private void cronTriggerStart(String start) throws SchedulerException {
        JobDetail jobDetailStart = JobBuilder.newJob(ConfigMbAppJob.class).withIdentity(jobKeyStart).build();
        Trigger triggerStart = TriggerBuilder.newTrigger().withIdentity(JOB_KEY_START_TRIGGER_NAME, JOB_KEY_START_TRIGGER_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(start))
                .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // Listener attached to jobKey
        scheduler.getListenerManager().addJobListener(new ConfigMbAppJobListener(), KeyMatcher.keyEquals(jobKeyStart));
        scheduler.start();
        scheduler.scheduleJob(jobDetailStart, triggerStart);
    }

}
