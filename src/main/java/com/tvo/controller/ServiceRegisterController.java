package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterReqDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ServiceRegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/service-register", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "ServiceRegisterController")
public class ServiceRegisterController {

    @Autowired
    ServiceRegisterService serviceRegisterService;

    @GetMapping(value = "/search")
    public ResponeData<List> searchCity(@Valid ServiceRegisterReqDto serviceRegisterReqDto) {
        List serviceRegisterResDtoList = serviceRegisterService.getListServiceRegister(serviceRegisterReqDto);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterResDtoList);
    }

}
