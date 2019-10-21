package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.ConfigMbAppDto;
import com.tvo.dto.ConfigMbAppRsDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ConfigMbAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/configMbApp")
public class ConfigMbAppController {

    @Autowired
    private ConfigMbAppService configMbAppService;

    @GetMapping("/getTransaction")
    public ResponeData<List<String>> findTypesByCode(@RequestParam String code) {
        List<String> types = configMbAppService.getTypeByCode(code);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, types);
    }

    @GetMapping("/detailTransaction")
    public ResponeData<List<ConfigMbAppRsDto>> findTypesByCode(@RequestParam String type, @RequestParam String code) {
        List<ConfigMbAppRsDto> configMbAppRsDtos = configMbAppService.findByTypeAndAndCode(type, code);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, configMbAppRsDtos);
    }
}
