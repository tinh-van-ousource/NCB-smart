package com.tvo.service;

import java.util.List;

import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.ListTypeServiceRegisterDto;
import com.tvo.dto.ServiceRegisterDto;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceRegisterService {
    ResponeData<Page<ServiceRegisterDto>> search(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto, Pageable pageable);

    ContentResDto getServiceRegisterDetailById(Long id);

    ContentResDto updateServiceRegisterDetail(Long id, ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto);

    List<String> getAllService();
    
    List<String> getListTypeServiceMbapp();
}
