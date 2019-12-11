package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ParCardSearchReqDto;
import com.tvo.dao.ParCardProductDao;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.request.ParCardProductUpdateReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParCardProductServiceImpl implements ParCardProductService {

    @Autowired
    ParCardProductDao parCardProductDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ParCardProductResDto> search(ParCardSearchReqDto searchModel, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ParCardProductEntity> query = cb.createQuery(ParCardProductEntity.class);
        Object[] queryObjs = this.createRootPersist(cb, query, searchModel);
        Root<ParCardProductEntity> root = (Root<ParCardProductEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("prdcode")));

        TypedQuery<ParCardProductEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ParCardProductEntity> objects = typedQuery.getResultList();
        List<ParCardProductResDto> objectDtos = ModelMapperUtils.mapAll(objects, ParCardProductResDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ParCardProductEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(objectDtos, pageable, total);
    }

    private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ParCardSearchReqDto resource) {
        final Root<ParCardProductEntity> rootPersist = query.from(ParCardProductEntity.class);
        final List<Predicate> predicates = new ArrayList<Predicate>(3);

        if (resource.getPrdcode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPrdcode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("prdcode")),"%" + resource.getPrdcode()+"%")));
        }

        if (resource.getProduct() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProduct().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("product")),"%" + resource.getProduct()+"%")));
        }

        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("status")), "%" +resource.getStatus()+"%")));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public ParCardProductResDto findByPrdcode(String prdcode) {
        ParCardProductEntity parCardProductEntity = parCardProductDao.findByPrdcode(prdcode);
        if (parCardProductEntity == null) {
            return null;
        }

        return ModelMapperUtils.map(parCardProductEntity, ParCardProductResDto.class);
    }

    @Override
    public ParCardProductResDto update(ParCardProductCreateReqDto request) {
    	ParCardProductEntity findByPrdcode = parCardProductDao.findByPrdcode(request.getPrdcode());
        if (ObjectUtils.isEmpty(findByPrdcode)) {
            fileService.deleteImage(AppConstant.RESOURCE_IMG, request.getFileName());
            return null;
        }

        ParCardProductEntity data = ModelMapperUtils.map(request, ParCardProductEntity.class);
        ParCardProductEntity save = parCardProductDao.save(data);

        return ModelMapperUtils.map(save, ParCardProductResDto.class);
    }

    @Override
    public boolean delete(String prdCode) {
        ParCardProductEntity parCardProductEntity = parCardProductDao.findByPrdcode(prdCode);
        if (parCardProductEntity != null) {
//            parCardProductEntity.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
            parCardProductDao.delete(parCardProductEntity);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public ParCardProductResDto create(ParCardProductCreateReqDto request) {
        ParCardProductEntity findByPrdcode = parCardProductDao.findByPrdcode(request.getPrdcode());
        if (!ObjectUtils.isEmpty(findByPrdcode)) {
            fileService.deleteImage(AppConstant.RESOURCE_IMG, request.getFileName());
            return null;
        }

        ParCardProductEntity data = ModelMapperUtils.map(request, ParCardProductEntity.class);
        data.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        ParCardProductEntity save = parCardProductDao.save(data);

        return ModelMapperUtils.map(save, ParCardProductResDto.class);
    }
}
