package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.dto.ServiceRegisterGetDetailResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ServiceRegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/service-register", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "ServiceRegisterController")
public class ServiceRegisterController {

    private ServiceRegisterService serviceRegisterService;

    public ServiceRegisterController(ServiceRegisterService serviceRegisterService) {
        this.serviceRegisterService = serviceRegisterService;
    }

    @GetMapping(value = "/search")
    public ResponeData<List> searchServiceRegister(@Valid ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        List serviceRegisterResDtoList =
                serviceRegisterService.getServiceRegisterList(serviceRegisterSearchReqDto);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterResDtoList);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<ServiceRegisterGetDetailResDto> getDetailById(@PathVariable("id") Long id) {
        ServiceRegisterGetDetailResDto serviceRegisterGetDetailResDto =
                serviceRegisterService.getServiceRegisterDetailById(id);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterGetDetailResDto);
    }

}
