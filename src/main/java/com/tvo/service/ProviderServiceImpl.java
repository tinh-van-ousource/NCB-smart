package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dao.ProviderDAO;
import com.tvo.dto.ProviderResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ProviderEntity;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderDAO providerDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ProviderResDto> search(SearchProviderReqDto searchProviderReqDto, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ProviderEntity> query = cb.createQuery(ProviderEntity.class);
        Object[] queryObjs = this.createProviderRootPersist(cb, query, searchProviderReqDto);
        Root<ProviderEntity> root = (Root<ProviderEntity>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.desc(root.get("id")));

        TypedQuery<ProviderEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ProviderEntity> objects = typedQuery.getResultList();
        List<ProviderResDto> providerResDtos = ModelMapperUtils.mapAll(objects, ProviderResDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ProviderEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(providerResDtos, pageable, total);
    }

    private Object[] createProviderRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchProviderReqDto resource) {
        final Root<ProviderEntity> rootPersist = query.from(ProviderEntity.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getProviderCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProviderCode().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("providerCode"), resource.getProviderCode())));
        }

        if (resource.getProviderName() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProviderName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("providerName")),
                    "%" + resource.getProviderName().toUpperCase() + "%")));
        }

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
    public ProviderResDto detail(Long id) {
        Optional<ProviderEntity> existedProvider = providerDao.findById(id);
        return existedProvider.map(providerEntity -> ModelMapperUtils.map(providerEntity, ProviderResDto.class)).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        Optional<ProviderEntity> existedProvider = providerDao.findById(id);
        if (existedProvider.isPresent()) {
            ProviderEntity providerEntity = existedProvider.get();
            providerEntity.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
            providerDao.save(providerEntity);
            return true;
        }
        return false;
    }

    @Override
    public ProviderResDto create(ProviderCreateReqDto request) {
        ProviderEntity providerEntity = providerDao.findByProviderCode(request.getProviderCode());
        if (providerEntity != null) {
            return null;
        }

        providerEntity = ModelMapperUtils.map(request, ProviderEntity.class);
        providerEntity.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        ProviderEntity save = providerDao.save(providerEntity);
        return ModelMapperUtils.map(save, ProviderResDto.class);
    }

    @Override
    public ProviderResDto update(ProviderUpdateReqDto request) {
        Optional<ProviderEntity> existedProvider = providerDao.findById(request.getId());
        if (existedProvider.isPresent()) {
            ProviderEntity entity = providerDao.save(ModelMapperUtils.map(request, ProviderEntity.class));
            return ModelMapperUtils.map(entity, ProviderResDto.class);
        }
        return null;
    }

}
