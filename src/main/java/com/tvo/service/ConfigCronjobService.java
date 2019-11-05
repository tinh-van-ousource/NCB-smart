package com.tvo.service;

import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.model.ConfigCronjob;
import com.tvo.model.ConfigMbApp;

public interface ConfigCronjobService {

    ConfigCronjob saveOrUpdate(ConfigCronjobRqDto configCronjobRqDto);

    ConfigMbApp saveOrUpdateConfigValue(String newValue);
}
