package com.tvo.service;

import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.dto.ServiceRegisterGetDetailResDto;

import java.util.List;

public interface ServiceRegisterService {
    List getServiceRegisterList(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto);

    ServiceRegisterGetDetailResDto getServiceRegisterDetailById(Long id);
}
