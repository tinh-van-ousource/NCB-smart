package com.tvo.service;

import com.tvo.common.ColumnName;
import com.tvo.common.ModelMapperUtils;
import com.tvo.common.TableName;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dao.ServiceRegisterLogRepo;
import com.tvo.dao.ServiceRegisterRepo;
import com.tvo.dto.*;
import com.tvo.enums.ServiceRegisterSearchType;
import com.tvo.model.ServiceRegisterEntity;
import com.tvo.model.ServiceRegisterLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService {

    @Autowired
    private ServiceRegisterRepo serviceRegisterRepo;

    @Autowired
    private ServiceRegisterLogRepo serviceRegisterLogRepo;

    @Override
    public ContentResDto getServiceRegisterList(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto) {
        serviceRegisterSearchReqDto.setFromDate(serviceRegisterSearchReqDto.getFromDate().replace("/", ""));
        serviceRegisterSearchReqDto.setToDate(serviceRegisterSearchReqDto.getToDate().replace("/", ""));

        List<ServiceRegisterEntity> serviceRegisterEntityList = serviceRegisterRepo.retrieveListServiceRegister(serviceRegisterSearchReqDto);
        Long totalServiceRegister = serviceRegisterRepo.retrieveListServiceRegisterCount(serviceRegisterSearchReqDto);

        ContentResDto contentResDto = new ContentResDto(Collections.EMPTY_LIST, 0L);

        if (!serviceRegisterEntityList.isEmpty()) {
            List serviceRegisterSearchResDtoList;

            convertStringToDateForServiceRegisterList(serviceRegisterEntityList);

//            if (serviceRegisterSearchReqDto.getType() != null
//                    && serviceRegisterSearchReqDto.getType().equals(ServiceRegisterSearchType.ACCOUNT.getType())) {
                serviceRegisterSearchResDtoList =
                        ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterSearchCardResDto.class);
//            } else {
//                serviceRegisterSearchResDtoList =
//                        ModelMapperUtils.mapAll(serviceRegisterEntityList, ServiceRegisterSearchAllResDto.class);
//            }

            contentResDto.setContent(serviceRegisterSearchResDtoList);
            contentResDto.setTotal(totalServiceRegister);
        }
        return contentResDto;
    }

    @Override
    public ContentResDto getServiceRegisterDetailById(Long id) {
        ServiceRegisterGetDetailResDto serviceRegisterGetDetailResDto = new ServiceRegisterGetDetailResDto();
        Optional<ServiceRegisterEntity> serviceRegisterEntity = serviceRegisterRepo.findById(id);
        ContentResDto contentResDto = new ContentResDto();

        if (serviceRegisterEntity.isPresent()) {
            serviceRegisterGetDetailResDto = ModelMapperUtils.map(serviceRegisterEntity.get(), ServiceRegisterGetDetailResDto.class);
            List<ServiceRegisterLogEntity> serviceRegisterLogEntityList = serviceRegisterRepo.retrieveServiceRegisterLogByServiceRegisterId(id);
            serviceRegisterGetDetailResDto.setServiceRegisterLogResDtoList(serviceRegisterLogEntityList
                    .stream()
                    .map(s -> ModelMapperUtils.map(s, ServiceRegisterLogResDto.class))
                    .collect(Collectors.toList()));
            contentResDto.setContent(serviceRegisterGetDetailResDto);
        }
        return contentResDto;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ContentResDto updateServiceRegisterDetail(Long id, ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Optional<ServiceRegisterEntity> serviceRegisterEntityOptional = serviceRegisterRepo.findById(id);
        ContentResDto contentResDto = new ContentResDto();

        if (serviceRegisterEntityOptional.isPresent()) {
            ServiceRegisterEntity serviceRegisterEntityOld = serviceRegisterEntityOptional.get();
            List<ServiceRegisterLogEntity> serviceRegisterLogEntityList = new ArrayList<>();
            ServiceRegisterEntity serviceRegisterEntityNew = ModelMapperUtils.map(serviceRegisterEntityOld, new ServiceRegisterEntity());

            if (serviceRegisterUpdateReqDto.getCompCode() != null &&
                    !serviceRegisterUpdateReqDto.getCompCode().equals(serviceRegisterEntityOld.getCompCode())) {

                serviceRegisterEntityNew.setCompCode(serviceRegisterUpdateReqDto.getCompCode());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity(
                        null, id, TableName.SERVICE_REGISTER_MBAPP, ColumnName.COMP_CODE,
                        serviceRegisterEntityOld.getCompCode(),
                        serviceRegisterEntityNew.getCompCode(),
                        serviceRegisterUpdateReqDto.getUserId(), currentLocalDateTime
                );
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getCompName() != null &&
                    !serviceRegisterUpdateReqDto.getCompName().equals(serviceRegisterEntityOld.getCompName())) {
                serviceRegisterEntityNew.setCompName(serviceRegisterUpdateReqDto.getCompName());

                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity(
                        null, id, TableName.SERVICE_REGISTER_MBAPP, ColumnName.COMP_NAME,
                        serviceRegisterEntityOld.getCompName(),
                        serviceRegisterEntityNew.getCompName(),
                        serviceRegisterUpdateReqDto.getUserId(), currentLocalDateTime
                );
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getStatus() != null && !serviceRegisterUpdateReqDto.getStatus().equals(serviceRegisterEntityOld.getStatus())) {
                serviceRegisterEntityNew.setStatus(serviceRegisterUpdateReqDto.getStatus());
                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity(
                        null, id, TableName.SERVICE_REGISTER_MBAPP, ColumnName.STATUS,
                        serviceRegisterEntityOld.getStatus(),
                        serviceRegisterEntityNew.getStatus(),
                        serviceRegisterUpdateReqDto.getUserId(), currentLocalDateTime
                );
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (serviceRegisterUpdateReqDto.getComment() != null) {
                serviceRegisterEntityNew.setStatus(serviceRegisterUpdateReqDto.getStatus());
                ServiceRegisterLogEntity serviceRegisterLogEntity = new ServiceRegisterLogEntity(
                        null, id, TableName.SERVICE_REGISTER_MBAPP, ColumnName.COMMENT,
                        null,
                        serviceRegisterUpdateReqDto.getComment(),
                        serviceRegisterUpdateReqDto.getUserId(), currentLocalDateTime
                );
                serviceRegisterLogEntityList.add(serviceRegisterLogEntity);
            }

            if (!serviceRegisterLogEntityList.isEmpty()) {
                // save all log
                serviceRegisterLogRepo.saveAll(serviceRegisterLogEntityList);

                // save new detail then return
                contentResDto.setContent(getRegisterServiceDetail(serviceRegisterRepo.save(serviceRegisterEntityNew)));
            } else {
                // return old detail
                contentResDto.setContent(getRegisterServiceDetail(serviceRegisterEntityOld));
            }
        }
        return contentResDto;
    }

    @Override
    public List<String> getAllService() {
        return serviceRegisterRepo.retrieveAllService();
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

    private void convertStringToDateForServiceRegisterList(List<ServiceRegisterEntity> serviceRegisterEntityList) {
        serviceRegisterEntityList.forEach(sre -> {
            String requestDate = sre.getRequestDate();
            String completedDate = sre.getCompletedDate();
            if (requestDate != null) {
                sre.setRequestDate(
                        requestDate.substring(0,4) + "/" +
                        requestDate.substring(4,6) + "/" +
                        requestDate.substring(6,8)
                );
            }
            if (completedDate != null) {
                sre.setCompletedDate(
                        completedDate.substring(0,4) + "/" +
                        completedDate.substring(4,6) + "/" +
                        completedDate.substring(6,8)
                );
            }
        });
    }

	@Override
	public List<String> getListTypeServiceMbapp() {
		return serviceRegisterRepo.getListTypeService();
	}

}
