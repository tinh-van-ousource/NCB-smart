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
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.controllerDto.searchCity;
import com.tvo.dao.DatUserProfileDao;
import com.tvo.dto.DatUserProfileDto;
import com.tvo.model.City;
import com.tvo.model.DatUserProfile;

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

	public Object[] createCityRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchDatUserProfileModel searchModel) {
		final Root<DatUserProfile> rootPersist = query.from(DatUserProfile.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (searchModel.getBrncode() != null
				&& !StringUtils.isEmpty(searchModel.getBrncode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("brncode"), searchModel.getBrncode())));
		}
		if (searchModel.getOfficecode() != null
				&& !StringUtils.isEmpty(searchModel.getOfficecode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("officecode"), searchModel.getOfficecode())));
		}
		if (searchModel.getUsrfname() != null
				&& !StringUtils.isEmpty(searchModel.getUsrfname().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("usrfname"), searchModel.getUsrfname())));
		}
		if (searchModel.getCifname() != null
				&& !StringUtils.isEmpty(searchModel.getCifname().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("cifname"), searchModel.getCifname())));
		}

		if (searchModel.getUsrstatus() != null
				&& !StringUtils.isEmpty(searchModel.getUsrstatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("usrstatus"), searchModel.getUsrstatus())));
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
		Object[] queryObjs = this.createCityRootPersist(cb, query, searchModel);
		query.select((Root<DatUserProfile>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<DatUserProfile> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<DatUserProfile> objects = typedQuery.getResultList();
		List<DatUserProfileDto> datUserProfileDtos = ModelMapperUtils.mapAll(objects, DatUserProfileDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
		countQuery.select(cbTotal.count(countQuery.from(DatUserProfile.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(datUserProfileDtos, pageable, total);
//		return null;
	}
}
