package com.tvo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dao.DatUserProfileDao;
import com.tvo.dto.DatUserProfileDto;
import com.tvo.model.DatCfmast;
import com.tvo.model.DatUserProfile;
import com.tvo.model.DatUserProfile_;
import com.tvo.model.Function;

@Service
public class DatUserProfileServiceImpl implements DatUserProfileService {
    

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DatUserProfileDao datUserProfileDao;

	@Autowired
	private EntityManager entityManager;

	public DatUserProfileServiceImpl() {
	}

	public DatUserProfileServiceImpl(EntityManagerFactory entityManagerFactory, DatUserProfileDao datUserProfileDao,
			EntityManager entityManager) {
		this.entityManagerFactory = entityManagerFactory;
		this.datUserProfileDao = datUserProfileDao;
		this.entityManager = entityManager;
	}

	@Override
	public List<DatUserProfileDto> findAll() {
		List<DatUserProfileDto> listResult = ModelMapperUtils.mapAll(datUserProfileDao.findAll(),
				DatUserProfileDto.class);
		return listResult;
	}

	public Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchDatUserProfileModel resource) {
		final Root<DatUserProfile> rootPersist = query.from(DatUserProfile.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();
		rootPersist.join(DatUserProfile_.datCfmast);

		if (resource.getUsrid() != null && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getUsrid().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("usrid")), "%" + resource.getUsrid().toUpperCase() + "%")));
		}
		if (resource.getCifgrp() != null && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCifgrp().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("cifgrp")), "%" + resource.getCifgrp().toUpperCase() + "%")));
		}
		if (resource.getIdno() != null && !StringUtils.isEmpty(resource.getIdno().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<DatCfmast>get("datCfmast").get("idno")), "%" + resource.getIdno().toUpperCase() + "%")));
		}
		if (resource.getFromDate() != null && resource.getToDate() != null) {
			predicates.add(cb.between(rootPersist.<Function>get("function").get("createdDate"), resource.getFromDate(), resource.getToDate()));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<DatUserProfileDto> searchDatUserProfile(SearchDatUserProfileModel searchModel, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<DatUserProfile> query = cb.createQuery(DatUserProfile.class);
		Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel);
		Root<DatUserProfile> root = (Root<DatUserProfile>) queryObjs[0];
		query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.asc(root.get("usrsname")));
		TypedQuery<DatUserProfile> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<DatUserProfile> objects = typedQuery.getResultList();
		List<DatUserProfileDto> datUserProfileDtos = ModelMapperUtils.mapAll(objects, DatUserProfileDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(DatUserProfile.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(datUserProfileDtos, pageable, total);

	}

	@Override
	public Page<DatUserProfileDto> searchConsumer(SearchConsumerModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<DatUserProfile> query = cb.createQuery(DatUserProfile.class);
		Object[] queryObjs = this.createConsumerRootPersist(cb, query, searchModel);
		Root<DatUserProfile> root = (Root<DatUserProfile>) queryObjs[0];
		query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<DatUserProfile> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<DatUserProfile> objects = typedQuery.getResultList();
		List<DatUserProfileDto> datUserProfileDtos = ModelMapperUtils.mapAll(objects, DatUserProfileDto.class);

		CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(DatUserProfile.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(datUserProfileDtos, pageable, total);
	}

	private Object[] createConsumerRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchConsumerModel searchModel) {
		final Root<DatUserProfile> rootPersist = query.from(DatUserProfile.class);
		rootPersist.join(DatUserProfile_.function);
		rootPersist.join(DatUserProfile_.datCfmast);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getUsrid() != null && !StringUtils.isEmpty(searchModel.getUsrid().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.get("usrid")), searchModel.getUsrid().toUpperCase())));
		}

		if (searchModel.getCifgrp() != null && !StringUtils.isEmpty(searchModel.getCifgrp().trim())) {
			predicates
					.add(cb.and(cb.equal(cb.upper(rootPersist.get("cifgrp")), searchModel.getCifgrp().toUpperCase())));
		}

		if (searchModel.getIdno() != null && !StringUtils.isEmpty(searchModel.getIdno().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<DatCfmast>get("datCfmast").get("idno")),
					searchModel.getIdno().toUpperCase())));
		}

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

}
