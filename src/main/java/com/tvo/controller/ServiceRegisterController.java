package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ServiceRegisterService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/service-register", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "ServiceRegisterController")
public class ServiceRegisterController {

    private ServiceRegisterService serviceRegisterService;

    public ServiceRegisterController(ServiceRegisterService serviceRegisterService) {
        this.serviceRegisterService = serviceRegisterService;
    }

    @GetMapping(value = "/search")
    public ResponeData<ContentResDto> searchServiceRegister(@Valid ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        ContentResDto serviceRegisterResDtoList =
                serviceRegisterService.getServiceRegisterList(serviceRegisterSearchReqDto);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterResDtoList);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<ContentResDto> getDetailById(@PathVariable("id") Long id) {
        ContentResDto serviceRegisterGetDetailResDto =
                serviceRegisterService.getServiceRegisterDetailById(id);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterGetDetailResDto);
    }

    @PatchMapping(value = "/{service_id}/update")
    public ResponeData<ContentResDto> updateServiceRegisterDetail(@PathVariable("service_id") Long id,
                                                                  @RequestBody ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto) {
        ContentResDto serviceRegisterGetDetailResDto =
                serviceRegisterService.updateServiceRegisterDetail(id, serviceRegisterUpdateReqDto);
        if (serviceRegisterGetDetailResDto != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterGetDetailResDto);
        } else {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, serviceRegisterGetDetailResDto);
        }
    }

}
