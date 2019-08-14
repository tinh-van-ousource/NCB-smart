package com.tvo.service;

import com.tvo.common.ColumnName;
import com.tvo.common.ModelMapperUtils;
import com.tvo.common.TableName;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dao.ServiceRegisterLogRepo;
import com.tvo.dao.ServiceRegisterRepo;
import com.tvo.dto.ServiceRegisterGetDetailResDto;
import com.tvo.dto.ServiceRegisterLogResDto;
import com.tvo.dto.ServiceRegisterSearchAllResDto;
import com.tvo.dto.ServiceRegisterSearchCardResDto;
import com.tvo.model.ServiceRegisterEntity;
import com.tvo.model.ServiceRegisterLogEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author NgocDC
 */
@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService {

    private ServiceRegisterRepo serviceRegisterRepo;
    private ServiceRegisterLogRepo serviceRegisterLogRepo;

    public ServiceRegisterServiceImpl(ServiceRegisterRepo serviceRegisterRepo,
                                      ServiceRegisterLogRepo serviceRegisterLogRepo) {
        this.serviceRegisterRepo = serviceRegisterRepo;
        this.serviceRegisterLogRepo = serviceRegisterLogRepo;
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

            List<ServiceRegisterLogEntity> serviceRegisterLogEntityList =
                    serviceRegisterRepo.retrieveServiceRegisterLogByServiceRegisterId(id);

            serviceRegisterGetDetailResDto.setServiceRegisterLogResDtoList(serviceRegisterLogEntityList
                    .stream()
                    .map(s -> ModelMapperUtils.map(s, ServiceRegisterLogResDto.class))
                    .collect(Collectors.toList()));
        }

        return serviceRegisterGetDetailResDto;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ServiceRegisterGetDetailResDto updateServiceRegisterDetail(Long id, ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        Optional<ServiceRegisterEntity> serviceRegisterEntityOptional = serviceRegisterRepo.findById(id);

        if (serviceRegisterEntityOptional.isPresent()) {
            ServiceRegisterEntity serviceRegisterEntityOld = serviceRegisterEntityOptional.get();

            List<ServiceRegisterLogEntity> serviceRegisterLogEntityList = new ArrayList<>();
            ServiceRegisterEntity serviceRegisterEntityNew =
                    ModelMapperUtils.map(serviceRegisterEntityOld, new ServiceRegisterEntity());

            if (serviceRegisterUpdateReqDto.getCompCode() != null &&
                    !serviceRegisterUpdateReqDto.getCompCode().equals(serviceRegisterEntityOld.getCompCode())) {

                serviceRegisterEntityNew.setCompCode(serviceRegisterUpdateReqDto.getCompCode());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity();
                serviceRegisterLogEntity.setServiceRegisterId(id);
                serviceRegisterLogEntity.setTableName(TableName.SERVICE_REGISTER_MBAPP);
                serviceRegisterLogEntity.setColumnName(ColumnName.COMP_CODE);
                serviceRegisterLogEntity.setOldValue(serviceRegisterEntityOld.getCompCode());
                serviceRegisterLogEntity.setNewValue(serviceRegisterEntityNew.getCompCode());
                serviceRegisterLogEntity.setUserId(serviceRegisterUpdateReqDto.getUserId());
                serviceRegisterLogEntity.setDatetime(currentLocalDateTime);
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getCompName() != null &&
                    !serviceRegisterUpdateReqDto.getCompName().equals(serviceRegisterEntityOld.getCompName())) {
                serviceRegisterEntityNew.setCompName(serviceRegisterUpdateReqDto.getCompName());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity();
                serviceRegisterLogEntity.setServiceRegisterId(id);
                serviceRegisterLogEntity.setTableName(TableName.SERVICE_REGISTER_MBAPP);
                serviceRegisterLogEntity.setColumnName(ColumnName.COMP_NAME);
                serviceRegisterLogEntity.setOldValue(serviceRegisterEntityOld.getCompName());
                serviceRegisterLogEntity.setNewValue(serviceRegisterEntityNew.getCompName());
                serviceRegisterLogEntity.setUserId(serviceRegisterUpdateReqDto.getUserId());
                serviceRegisterLogEntity.setDatetime(currentLocalDateTime);
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getStatus() != null &&
                    !serviceRegisterUpdateReqDto.getStatus().equals(serviceRegisterEntityOld.getStatus())) {
                serviceRegisterEntityNew.setStatus(serviceRegisterUpdateReqDto.getStatus());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity();
                serviceRegisterLogEntity.setServiceRegisterId(id);
                serviceRegisterLogEntity.setTableName(TableName.SERVICE_REGISTER_MBAPP);
                serviceRegisterLogEntity.setColumnName(ColumnName.STATUS);
                serviceRegisterLogEntity.setOldValue(serviceRegisterEntityOld.getStatus());
                serviceRegisterLogEntity.setNewValue(serviceRegisterEntityNew.getStatus());
                serviceRegisterLogEntity.setUserId(serviceRegisterUpdateReqDto.getUserId());
                serviceRegisterLogEntity.setDatetime(currentLocalDateTime);
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getComment() != null) {
                serviceRegisterEntityNew.setStatus(serviceRegisterUpdateReqDto.getStatus());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity();
                serviceRegisterLogEntity.setServiceRegisterId(id);
                serviceRegisterLogEntity.setTableName(TableName.SERVICE_REGISTER_MBAPP);
                serviceRegisterLogEntity.setColumnName(ColumnName.COMMENT);
                serviceRegisterLogEntity.setNewValue(serviceRegisterUpdateReqDto.getComment());
                serviceRegisterLogEntity.setUserId(serviceRegisterUpdateReqDto.getUserId());
                serviceRegisterLogEntity.setDatetime(currentLocalDateTime);
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (!serviceRegisterLogEntityList.isEmpty()) {
                // save all log
                serviceRegisterLogRepo.saveAll(serviceRegisterLogEntityList);

                // save new detail then return
                return getRegisterServiceDetail(serviceRegisterRepo.save(serviceRegisterEntityNew));
            } else {
                // return old detail
                return getRegisterServiceDetail(serviceRegisterEntityOld);
            }
        }

        return null;
    }

    private ServiceRegisterGetDetailResDto getRegisterServiceDetail(ServiceRegisterEntity serviceRegisterEntity) {
        ServiceRegisterGetDetailResDto serviceRegisterGetDetailResDto = ModelMapperUtils.map(
                serviceRegisterEntity,
                ServiceRegisterGetDetailResDto.class);

        // return all service register log
        List<ServiceRegisterLogEntity> serviceRegisterLogEntityAllList =
                serviceRegisterRepo.retrieveServiceRegisterLogByServiceRegisterId(serviceRegisterEntity.getId());

        serviceRegisterGetDetailResDto.setServiceRegisterLogResDtoList(serviceRegisterLogEntityAllList
                .stream()
                .map(s -> ModelMapperUtils.map(s, ServiceRegisterLogResDto.class))
                .collect(Collectors.toList()));

        return serviceRegisterGetDetailResDto;
    }

}
