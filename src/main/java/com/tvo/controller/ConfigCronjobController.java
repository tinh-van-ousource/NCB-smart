package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.service.job.CronConfigNtoY;
import com.tvo.service.job.CronConfigYtoN;
import com.tvo.service.quartz.JobService;
import com.tvo.model.ConfigCronjob;
import com.tvo.response.ResponeData;
import com.tvo.service.ConfigCronjobService;
import com.tvo.service.quartz.JobUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/config-cronjob")
public class ConfigCronjobController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String GROUP_KEY_CONFIG = "GROUP_KEY_CONFIG";
    private static final String JOB_KEY_NAME_START = "JOB_KEY_NAME_START";
    private static final String JOB_KEY_NAME_END = "JOB_KEY_NAME_END";

    @Autowired
    private ConfigCronjobService configCronjobService;

    @Autowired
    private JobService jobService;

    @PostMapping("/saveOrUpdate")
    public ResponeData<ConfigCronjob> findTypesByCode(@RequestBody ConfigCronjobRqDto configCronjobRqDto) {
        ConfigCronjob configCronjob = configCronjobService.saveOrUpdate(configCronjobRqDto);
        if (configCronjob != null) {
            cronTrigger(CronConfigNtoY.class, JOB_KEY_NAME_START, configCronjob.getTimeStart());
            cronTrigger(CronConfigYtoN.class, JOB_KEY_NAME_END, configCronjob.getTimeEnd());
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, configCronjob);
    }

    private boolean cronTrigger(Class<? extends QuartzJobBean> jobClass, String jobKey, String timeExpression) {
        if (timeExpression == null || timeExpression.trim().equals("")) {
            return false;
        }
        if (!jobService.isJobWithNamePresent(jobKey, GROUP_KEY_CONFIG)) {
            return jobService.scheduleCronJob(jobClass, jobKey, GROUP_KEY_CONFIG, timeExpression);
        } else {
            return jobService.updateCronJob(jobKey, timeExpression);
        }
    }
}
