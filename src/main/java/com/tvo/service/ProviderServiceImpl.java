package com.tvo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchProviderReqDto;
import com.tvo.dao.ProviderDAO;
import com.tvo.dto.CompDroplistBranchDto;
import com.tvo.dto.NcbBannerDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.dto.ProviderResDto;
import com.tvo.dto.RoleResDto;
import com.tvo.dto.ServiceMbappCodeListDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.NcbBanner;
import com.tvo.model.Promotions;
import com.tvo.model.ProviderEntity;
import com.tvo.request.ProviderCreateReqDto;
import com.tvo.request.ProviderUpdateReqDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderDAO providerDao;

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
        query.orderBy(cb.asc(root.get("providerName")));
        
//        query.orderBy(cb.desc(root.get("providerName")),cb.desc(root.get("name")));


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
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("providerCode")), "%" + resource.getProviderCode().toUpperCase() + "%")));
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
    public ProviderResDto detail(String providerCode) {
        ProviderEntity existedProvider = providerDao.findByProviderCode(providerCode);
        if (existedProvider != null) {
            return ModelMapperUtils.map(existedProvider, ProviderResDto.class);
        }
        // return existedProvider.map(providerEntity -> ModelMapperUtils.map(providerEntity, ProviderResDto.class)).orElse(null);
        return null;
    }

    @Override
    public boolean delete(String providerCode) {
        ProviderEntity existedProvider = providerDao.findByProviderCode(providerCode);
        if (existedProvider != null) {
            providerDao.delete(existedProvider);
            return true;
        }
        return false;
    }

    @Override
    public ProviderResDto create(ProviderCreateReqDto request) {
        ProviderEntity providerEntity = providerDao.findByProviderCodeAndServiceCodeAndPartner(request.getProviderCode(), request.getServiceCode(), request.getPartner());
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
        ProviderEntity providerEntity = providerDao.findByProviderCodeAndServiceCodeAndPartner(request.getProviderCode(), request.getServiceCode(), request.getPartner());
        if (providerEntity != null) {
        	ProviderEntity ncbBanner = ModelMapperUtils.map(request, ProviderEntity.class);
            ProviderEntity save = providerDao.save(ncbBanner);
            return ModelMapperUtils.map(save, ProviderResDto.class);
        }
        return null;
    }

	@Override
	public List<ServiceMbappCodeListDto> getServiceCodeList() {
		List<ServiceMbappCodeListDto> serviceCodeDtoList =new ArrayList<>();
		List<Object> listComp = providerDao.getListServiceCode();
        for (Object depart : listComp) {
            Object[] departs = (Object[]) depart;
            serviceCodeDtoList.add(
                    new ServiceMbappCodeListDto(departs[0].toString(), departs[1].toString()));
        }

        return serviceCodeDtoList;
	}

}
