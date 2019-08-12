package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.dao.ServiceRegisterRepo;
import com.tvo.dto.ServiceRegisterGetDetailResDto;
import com.tvo.dto.ServiceRegisterLogResDto;
import com.tvo.dto.ServiceRegisterSearchAllResDto;
import com.tvo.dto.ServiceRegisterSearchCardResDto;
import com.tvo.model.ServiceRegisterEntity;
import com.tvo.model.ServiceRegisterLogEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService {

    private ServiceRegisterRepo serviceRegisterRepo;

    public ServiceRegisterServiceImpl(ServiceRegisterRepo serviceRegisterRepo) {
        this.serviceRegisterRepo = serviceRegisterRepo;
    }

    @Override
    public List getServiceRegisterList(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        List<ServiceRegisterEntity> serviceRegisterEntityList =
                serviceRegisterRepo.retrieveListServiceRegister(serviceRegisterSearchReqDto);

        if (!serviceRegisterEntityList.isEmpty()) {
            if (serviceRegisterSearchReqDto.getType() != null) {
                return ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterSearchCardResDto.class);
            } else {
                return ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterSearchAllResDto.class);
            }
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public ServiceRegisterGetDetailResDto getServiceRegisterDetailById(Long id) {
        ServiceRegisterGetDetailResDto serviceRegisterGetDetailResDto = new ServiceRegisterGetDetailResDto();

        Optional<ServiceRegisterEntity> serviceRegisterEntity = serviceRegisterRepo.findById(id);

        if (serviceRegisterEntity.isPresent()) {
            serviceRegisterGetDetailResDto = ModelMapperUtils.map(serviceRegisterEntity.get(), ServiceRegisterGetDetailResDto.class);

            List<ServiceRegisterLogEntity> serviceRegisterLogEntity =
                    serviceRegisterRepo.retrieveServiceRegisterLogByServiceRegisterId(id);

            serviceRegisterGetDetailResDto.setServiceRegisterLogResDtoList(serviceRegisterLogEntity
                    .stream()
                    .map(s -> ModelMapperUtils.map(s, ServiceRegisterLogResDto.class))
                    .collect(Collectors.toList()));
        }

        return serviceRegisterGetDetailResDto;
    }

}
