package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.ColumnName;
import com.tvo.common.ModelMapperUtils;
import com.tvo.common.TableName;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dao.ServiceRegisterLogRepo;
import com.tvo.dao.ServiceRegisterRepo;
import com.tvo.dto.*;
import com.tvo.enums.ServiceRegisterSearchType;
import com.tvo.model.QrServiceEntity;
import com.tvo.model.ServiceRegisterEntity;
import com.tvo.model.ServiceRegisterLogEntity;
import com.tvo.response.ResponeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.InetAddress;
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

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponeData<Page<ServiceRegisterDto>> search(ServiceRegisterSearchReqDto serviceRegisterSearchReqDto,
                                                  Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ServiceRegisterEntity> query = cb.createQuery(ServiceRegisterEntity.class);
        Object[] queryObjs = this.createServiceRegisterRootPersist(cb, query, serviceRegisterSearchReqDto);
        Root<ServiceRegisterEntity> root = (Root<ServiceRegisterEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        TypedQuery<ServiceRegisterEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ServiceRegisterEntity> objects = typedQuery.getResultList();
        List<ServiceRegisterDto> serviceRegisterDtos = ModelMapperUtils.mapAll(objects, ServiceRegisterDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ServiceRegisterEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, new PageImpl<>(serviceRegisterDtos, pageable, total));
    }
    private Object[] createServiceRegisterRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ServiceRegisterSearchReqDto resource) {
        final Root<ServiceRegisterEntity> rootPersist = query.from(ServiceRegisterEntity.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (resource.getCompCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCompCode().trim())) {
            predicates.add(cb.and(cb.equal(cb.upper(rootPersist.get("compCode")), resource.getCompCode())));
        }
        if (resource.getIdCard() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getIdCard().trim())) {
            predicates.add(cb.and(cb.equal(cb.upper(rootPersist.get("idCard")), resource.getIdCard())));
        }
        if (resource.getService() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getService().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("service")), "%" + resource.getService().toUpperCase() + "%")));
        }
        if (resource.getType() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getType().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("type"), resource.getType())));
        }
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }
        if (resource.getFromDate() != null) {
            predicates.add(cb.and(cb.greaterThanOrEqualTo(rootPersist.get("idCardIssueDate"), resource.getFromDate())));
        }
        if (resource.getToDate() != null) {
            predicates.add(cb.and(cb.lessThanOrEqualTo(rootPersist.get("idCardIssueDate"), resource.getToDate())));
        }
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
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
