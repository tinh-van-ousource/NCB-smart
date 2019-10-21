package com.tvo.service;

import com.tvo.dto.ConfigMbAppRsDto;

import java.util.List;

public interface ConfigMbAppService {

    List<String> getTypeByCode(String code);

    List<ConfigMbAppRsDto> findByTypeAndAndCode(String type, String code);

}
