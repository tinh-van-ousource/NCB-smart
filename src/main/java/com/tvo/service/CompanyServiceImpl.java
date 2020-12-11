package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.CompanyCreateReqDto;
import com.tvo.controllerDto.CompanySearchReqDto;
import com.tvo.controllerDto.CompanyUpdateReqDto;
import com.tvo.dao.CompanyRepo;
import com.tvo.dto.CompanyResDto;
import com.tvo.model.CompanyEntity;
import com.tvo.model.CompanyEntityPK;
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

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public CompanyServiceImpl(CompanyRepo companyRepo, EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
        this.companyRepo = companyRepo;
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManager;
    }

    @Override
    public CompanyResDto create(CompanyCreateReqDto companyCreateReqDto) {
        CompanyEntity companyEntity = companyRepo
                .findByCompCodeAndMcnAndMp(companyCreateReqDto.getCompCode(),
                        companyCreateReqDto.getMcn(), companyCreateReqDto.getMp());
        if (companyEntity != null) {
            return null;
        }
        CompanyEntityPK entityPK = new CompanyEntityPK();
        companyEntity = ModelMapperUtils.map(companyCreateReqDto, CompanyEntity.class);
        entityPK.setCompCode(companyCreateReqDto.getCompCode());
        entityPK.setMcn(companyCreateReqDto.getMcn());
        entityPK.setMp(companyCreateReqDto.getMp());
        companyEntity.setKey(entityPK);
        return ModelMapperUtils.map(companyRepo.save(companyEntity), CompanyResDto.class);
    }

    @Override
    public CompanyResDto update(CompanyUpdateReqDto companyUpdateReqDto) {
        CompanyEntity companyEntity = companyRepo
                .findByCompCodeAndMcnAndMp(companyUpdateReqDto.getCompCode(),
                        companyUpdateReqDto.getMcn(), companyUpdateReqDto.getMp());
        if (companyEntity == null) {
            return null;
        }
        CompanyEntityPK entityPK = new CompanyEntityPK();
        companyEntity = ModelMapperUtils.map(companyUpdateReqDto, CompanyEntity.class);
        entityPK.setCompCode(companyUpdateReqDto.getCompCode());
        entityPK.setMcn(companyUpdateReqDto.getMcn());
        entityPK.setMp(companyUpdateReqDto.getMp());
        companyEntity.setKey(entityPK);
        return ModelMapperUtils.map(companyRepo.save(companyEntity), CompanyResDto.class);
    }

    @Override
    public Page<CompanyResDto> search(CompanySearchReqDto companySearchReqDto, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<CompanyEntity> query = cb.createQuery(CompanyEntity.class);
        Object[] queryObjs = this.createRootPersist(cb, query, companySearchReqDto);
        Root<CompanyEntity> root = (Root<CompanyEntity>) queryObjs[0];
        query.orderBy(cb.asc(root.get("compName")));
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<CompanyEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<CompanyEntity> objects = typedQuery.getResultList();
        List<CompanyResDto> companyResDtos = ModelMapperUtils.mapAll(objects, CompanyResDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(CompanyEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(companyResDtos, pageable, total);
    }

    private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, CompanySearchReqDto resource) {
        final Root<CompanyEntity> rootPersist = query.from(CompanyEntity.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();

        if (resource != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCompCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("key").get("compCode")), "%" + resource.getCompCode().toUpperCase() + "%")));
        }
        if (resource != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCompName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("compName")),
                    "%" + resource.getCompName().toUpperCase() + "%")));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public CompanyResDto detail(String compCode, String mcn, String mp) {
        CompanyEntity companyEntity = companyRepo.findByCompCodeAndMcnAndMp(compCode, mcn, mp);
        if (companyEntity != null) {
            return ModelMapperUtils.map(companyEntity, CompanyResDto.class);
        }
        return null;
    }

    @Override
    public Boolean delete(String compCode, String mcn, String mp) {
        try {
            CompanyEntity companyEntity = companyRepo.findByCompCodeAndMcnAndMp(compCode, mcn, mp);
            if (companyEntity != null) {
                companyRepo.delete(companyEntity);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
