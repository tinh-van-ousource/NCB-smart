package com.tvo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
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

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dao.NcbBannerDao;
import com.tvo.dto.NcbBannerDto;
import com.tvo.model.NcbBanner;
import com.tvo.request.CreateNcbBannerRequest;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 13, 2019
 */
@Service
public class NcbBannerServiceImpl implements NcbBannerService {

	@Autowired
	private NcbBannerDao ncbBannerDao;

	private final EntityManager entityManager;

	public NcbBannerServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NcbBannerDto> findAll() {
		return ModelMapperUtils.mapAll(ncbBannerDao.findAll(), NcbBannerDto.class);
	}

	@Override
	public NcbBannerDto findById(Long id) {
		NcbBanner ncbBanner = new NcbBanner();
		Optional<NcbBanner> opt = ncbBannerDao.findById(id);
		if (opt.isPresent()) {
			ncbBanner = opt.get();
		}
		return ModelMapperUtils.map(ncbBanner, NcbBannerDto.class);
	}

	@SuppressWarnings("unused")
	private Object[] createNcbBannerRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchNcbBannerModel searchModel) {
		final Root<NcbBanner> rootPersist = query.from(NcbBanner.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getBannerCode() != null && !StringUtils.isEmpty(searchModel.getBannerCode().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("bannerCode")),
					searchModel.getBannerCode().toUpperCase())));
		}
		if (searchModel.getBannerName() != null && !StringUtils.isEmpty(searchModel.getBannerName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("bannerName")),
					searchModel.getBannerName().toUpperCase())));
		}
		if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), searchModel.getStatus())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<NcbBannerDto> searchNcbBanner(SearchNcbBannerModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<NcbBanner> query = cb.createQuery(NcbBanner.class);
		Object[] queryObjs = createNcbBannerRootPersist(cb, query, searchModel);
		query.select((Root<NcbBanner>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<NcbBanner> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<NcbBanner> objects = typedQuery.getResultList();
		List<NcbBannerDto> ncbBannerDtos = ModelMapperUtils.mapAll(objects, NcbBannerDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(NcbBanner.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ncbBannerDtos, pageable, total);
	}

	@Override
	public NcbBannerDto create(CreateNcbBannerRequest request) {
		NcbBanner ncbBanner = ModelMapperUtils.map(request, NcbBanner.class);
		ncbBanner.setCreatedDate(LocalDateTime.now());
		ncbBanner.setStatus(AppConstant.STATUS_ACTIVED);
		return ModelMapperUtils.map(ncbBannerDao.save(ncbBanner), NcbBannerDto.class);
	}

	@Override
	public NcbBannerDto update(UpdateNcbBannerRequest request) {
		Optional<NcbBanner> opt = ncbBannerDao.findById(request.getId());
		if (opt.isPresent()) {
			NcbBanner ncbBanner = ModelMapperUtils.map(request, NcbBanner.class);
			ncbBanner.setCreatedDate(opt.get().getCreatedDate());

			NcbBanner save = ncbBannerDao.save(ncbBanner);

			return ModelMapperUtils.map(save, NcbBannerDto.class);
		}
		return null;
	}

	@Override
	public Boolean delete(Long id) {
		NcbBanner ncbBanner = new NcbBanner();
		if (id != null) {
			Optional<NcbBanner> opt = ncbBannerDao.findById(id);
			if (opt.isPresent()) {
				ncbBanner = opt.get();
				ncbBanner.setStatus(AppConstant.STATUS_DEACTIVED);
				ncbBannerDao.save(ncbBanner);
				return true;
			}
		}
		return false;
	}

}
