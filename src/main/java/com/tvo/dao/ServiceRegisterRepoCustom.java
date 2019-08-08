package com.tvo.dao;

import com.tvo.controllerDto.ServiceRegisterReqDto;
import com.tvo.model.ServiceRegisterEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRegisterRepoCustom {
    List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterReqDto serviceRegisterReqDto);
}
