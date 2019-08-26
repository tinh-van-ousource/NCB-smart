package com.tvo.service;

import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.ServiceRegisterGetDetailResDto;

public interface ServiceRegisterService {
    ContentResDto getServiceRegisterList(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto);

    ContentResDto getServiceRegisterDetailById(Long id);

    ContentResDto updateServiceRegisterDetail(Long id, ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto);
}
