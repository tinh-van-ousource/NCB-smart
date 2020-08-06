package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchQrServiceDto;
import com.tvo.dao.QrServiceDao;
import com.tvo.dto.QrServiceDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.QrServiceEntity;
import com.tvo.request.CreateQrService;
import com.tvo.request.UpdateQrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Service
public class QrServiceImpl implements QrService {
    @Autowired
    private QrServiceDao qrServiceDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<QrServiceDto> search(SearchQrServiceDto searchQrServiceDto, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<QrServiceEntity> query = cb.createQuery(QrServiceEntity.class);
        Object[] queryObjs = this.createQrServiceRootPersist(cb, query, searchQrServiceDto);
        Root<QrServiceEntity> root = (Root<QrServiceEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<QrServiceEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<QrServiceEntity> objects = typedQuery.getResultList();
        List<QrServiceDto> qrServiceDtos = ModelMapperUtils.mapAll(objects, QrServiceDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(QrServiceEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(qrServiceDtos, pageable, total);
    }

    private Object[] createQrServiceRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchQrServiceDto resource) {
        final Root<QrServiceEntity> rootPersist = query.from(QrServiceEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getTitle() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTitle().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("title")), "%" + resource.getTitle().toUpperCase() + "%")));
        }
        predicates.add(cb.and(cb.isNull(rootPersist.<LocalDateTime>get("deletedAt"))));
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public QrServiceDto create(CreateQrService createQrService) {
        QrServiceEntity qrServiceEntity = setQrServiceEntity(createQrService);
        QrServiceEntity save = qrServiceDao.save(qrServiceEntity);
        return ModelMapperUtils.map(save, QrServiceDto.class);
    }

    private QrServiceEntity setQrServiceEntity(CreateQrService createQrService) {
        QrServiceEntity qrServiceEntity = new QrServiceEntity();
        qrServiceEntity.setTitle(StringUtils.isEmpty(createQrService.getTitle()) ? null : createQrService.getTitle());
        qrServiceEntity.setServiceType(StringUtils.isEmpty(createQrService.getServiceType()) ? null : createQrService.getServiceType());
        qrServiceEntity.setStatus(StringUtils.isEmpty(createQrService.getStatus()) ? StatusActivate.STATUS_ACTIVATED.getStatus() : createQrService.getStatus());
        qrServiceEntity.setCreatedAt(LocalDateTime.now());
        qrServiceEntity.setCreatedBy(Flag.userFlag.getUserName());
        return qrServiceEntity;
    }

    @Override
    public QrServiceDto update(Long id, UpdateQrService updateQrService) {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity != null) {
            qrServiceEntity = setUpdate(qrServiceEntity, updateQrService);
            QrServiceEntity save = qrServiceDao.save(qrServiceEntity);
            return ModelMapperUtils.map(save, QrServiceDto.class);
        }
        return null;
    }

    private QrServiceEntity setUpdate(QrServiceEntity qrServiceEntity, UpdateQrService updateQrService) {
        if (!StringUtils.isEmpty(updateQrService.getTitle())) {
            qrServiceEntity.setTitle(updateQrService.getTitle());
        }
        if (!StringUtils.isEmpty(updateQrService.getServiceType())) {
            qrServiceEntity.setServiceType(updateQrService.getServiceType());
        }
        if (!StringUtils.isEmpty(updateQrService.getStatus())) {
            qrServiceEntity.setStatus(updateQrService.getStatus());
        }
        qrServiceEntity.setUpdatedAt(LocalDateTime.now());
        qrServiceEntity.setUpdatedBy(Flag.userFlag.getUserName());
        return qrServiceEntity;
    }

    @Override
    public QrServiceDto detail(Long id) {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity != null) {
            return ModelMapperUtils.map(qrServiceEntity, QrServiceDto.class);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        QrServiceEntity qrServiceEntity = qrServiceDao.findByIdNotDeleted(id);
        if (qrServiceEntity != null) {
            qrServiceEntity.setDeletedAt(LocalDateTime.now());
            qrServiceDao.save(qrServiceEntity);
            return true;
        }
        return false;
    }

}
