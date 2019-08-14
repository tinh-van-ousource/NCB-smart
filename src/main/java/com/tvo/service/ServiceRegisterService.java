package com.tvo.service;

import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dto.ServiceRegisterGetDetailResDto;

import java.util.List;

/**
 * @author NgocDC
 */
public interface ServiceRegisterService {
    List getServiceRegisterList(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto);

    ServiceRegisterGetDetailResDto getServiceRegisterDetailById(Long id);

    ServiceRegisterGetDetailResDto updateServiceRegisterDetail(Long id, ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto);
}
