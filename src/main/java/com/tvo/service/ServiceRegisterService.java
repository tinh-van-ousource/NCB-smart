package com.tvo.service;

import com.tvo.controllerDto.ServiceRegisterReqDto;

import java.util.List;

public interface ServiceRegisterService {
    List getListServiceRegister(ServiceRegisterReqDto serviceRegisterReqDto);
}
