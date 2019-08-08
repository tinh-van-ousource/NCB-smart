package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ServiceRegisterReqDto;
import com.tvo.dao.ServiceRegisterRepo;
import com.tvo.dto.ServiceRegisterAllResDto;
import com.tvo.dto.ServiceRegisterCardResDto;
import com.tvo.model.ServiceRegisterEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService {

    private ServiceRegisterRepo serviceRegisterRepo;

    public ServiceRegisterServiceImpl(ServiceRegisterRepo serviceRegisterRepo) {
        this.serviceRegisterRepo = serviceRegisterRepo;
    }

    @Override
    public List getListServiceRegister(ServiceRegisterReqDto serviceRegisterReqDto) {
        List<ServiceRegisterEntity> serviceRegisterEntityList =
                serviceRegisterRepo.retrieveListServiceRegister(serviceRegisterReqDto);

        if (!serviceRegisterEntityList.isEmpty()) {
            if (serviceRegisterReqDto.getType() != null) {
                return ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterCardResDto.class);
            } else {
                return ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterAllResDto.class);
            }
        } else {
            return Collections.EMPTY_LIST;
        }
    }

}
