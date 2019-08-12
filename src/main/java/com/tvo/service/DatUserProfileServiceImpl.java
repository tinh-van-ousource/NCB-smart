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

	public Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchDatUserProfileModel searchModel, String filter) {
		final Root<DatUserProfile> rootPersist = query.from(DatUserProfile.class);
		rootPersist.join(DatUserProfile_.function);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getBrncode() != null && !StringUtils.isEmpty(searchModel.getBrncode().trim())) {
			predicates.add(cb.and(
					cb.equal(cb.upper(rootPersist.<String>get("brncode")), searchModel.getBrncode().toUpperCase())));
		}
		if (searchModel.getOfficecode() != null && !StringUtils.isEmpty(searchModel.getOfficecode().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("officecode")),
					searchModel.getOfficecode().toUpperCase())));
		}
		if (searchModel.getUsrfname() != null && !StringUtils.isEmpty(searchModel.getUsrfname().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("usrfname")),
					"%" + searchModel.getUsrfname().toUpperCase() + "%")));
		}
		if (searchModel.getCifname() != null && !StringUtils.isEmpty(searchModel.getCifname().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("cifname")),
					"%" + searchModel.getCifname().toUpperCase() + "%")));
		}

		if (searchModel.getUsrstatus() != null && !StringUtils.isEmpty(searchModel.getUsrstatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("usrstatus"), searchModel.getUsrstatus())));
		}
		if (filter != null && !StringUtils.isEmpty(filter.trim())) {
			predicates.add(
					cb.and(cb.like(cb.upper(rootPersist.<String>get("usrfname")), "%" + filter.toUpperCase() + "%")));
			predicates.add(
					cb.or(cb.like(cb.upper(rootPersist.<String>get("cifname")), "%" + filter.toUpperCase() + "%")));
//			predicates.add(cb.or(cb.like(rootPersist.<String>get("usrid"), "%" +filter+ "%")));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	public Object[] createConsumerRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchConsumerModel searchModel, String filter) {
		final Root<DatUserProfile> rootPersist = query.from(DatUserProfile.class);
		rootPersist.join(DatUserProfile_.function);
		rootPersist.join(DatUserProfile_.datCfmast);
//		Join<DatUserProfile, DatCfmast> datCfmast = rootPersist.join(DatUserProfile_.datCfmast);
		final List<Predicate> predicates = new ArrayList<Predicate>();
		if (searchModel.getUsrid() != null && !StringUtils.isEmpty(searchModel.getUsrid().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("usrid")),
					"%" + searchModel.getUsrid().toUpperCase() + "%")));
		}
		if (searchModel.getCifgrp() != null && !StringUtils.isEmpty(searchModel.getCifgrp().trim())) {
			predicates.add(cb
					.and(cb.equal(cb.upper(rootPersist.<String>get("cifgrp")), searchModel.getCifgrp().toUpperCase())));
		}
		if (searchModel.getIdno() != null && !StringUtils.isEmpty(searchModel.getIdno().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<DatCfmast>get("datCfmast").<String>get("idno")),
					searchModel.getIdno().toUpperCase())));
		}
		if (filter != null && !StringUtils.isEmpty(filter.trim())) {
			predicates.add(
					cb.and(cb.like(cb.upper(rootPersist.<String>get("usrfname")), "%" + filter.toUpperCase() + "%")));
//			predicates.add(
//					cb.or(cb.like(cb.upper(rootPersist.<String>get("cifname")), "%" + filter.toUpperCase() + "%")));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<DatUserProfileDto> searchDatUserProfile(SearchDatUserProfileModel searchModel, String filter,
			Pageable pageable) {
		CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<DatUserProfile> query = cb.createQuery(DatUserProfile.class);
		Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel, filter);
		query.select((Root<DatUserProfile>) queryObjs[0]);
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
//		return null;
	}

	@Override
	public Page<DatUserProfileDto> searchConsumer(SearchConsumerModel searchModel, String filter, Pageable pageable) {
		CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<DatUserProfile> query = cb.createQuery(DatUserProfile.class);
		Object[] queryObjs = this.createConsumerRootPersist(cb, query, searchModel, filter);
		query.select((Root<DatUserProfile>) queryObjs[0]);
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

}
