package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.model.ConfigCronjob;
import com.tvo.response.ResponeData;
import com.tvo.service.ConfigCronjobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/config-cronjob")
public class ConfigCronjobController {

    @Autowired
    private ConfigCronjobService configCronjobService;

    @PostMapping("/saveOrUpdate")
    public ResponeData<ConfigCronjob> findTypesByCode(@RequestBody ConfigCronjobRqDto configCronjobRqDto) {
        ConfigCronjob configCronjob = configCronjobService.saveOrUpdate(configCronjobRqDto);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, configCronjob);
    }
}
