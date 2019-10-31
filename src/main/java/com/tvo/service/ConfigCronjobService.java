package com.tvo.service;

import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.model.ConfigCronjob;

public interface ConfigCronjobService {

    ConfigCronjob saveOrUpdate(ConfigCronjobRqDto configCronjobRqDto);
}
