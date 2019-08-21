package com.tvo.dao;

import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.model.ServiceRegisterEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author NgocDC
 */
@Repository
public interface ServiceRegisterRepoCustom {
    List<ServiceRegisterEntity> retrieveListServiceRegister(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto);

    Long retrieveListServiceRegisterCount(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto);
}
