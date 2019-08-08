package com.tvo.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dao.MbProvisionDao;
import com.tvo.dto.MbProvisionDto;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.DatUserProfile;
import com.tvo.model.DatUserProfile_;
import com.tvo.model.MbProvision;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateMbProvisionRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MbProvisionServiceImpl implements MbProvisionService {

	@Autowired
	private MbProvisionDao mbProvisionDao;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<MbProvisionDto> findAll() {
		return ModelMapperUtils.mapAll(mbProvisionDao.findAll(), MbProvisionDto.class);
	}

	@Override
	public MbProvision findById(String id) {
		MbProvision mbProvision = new MbProvision();
		try {
			Optional<MbProvision> opt = mbProvisionDao.findById(id);
			if (opt.isPresent()) {
				mbProvision = mbProvisionDao.findById(id).get();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return mbProvision;

	}

	@SuppressWarnings("unused")
	private Object[] createProvisionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchMbProvisionModel searchModel) {
		final Root<MbProvision> rootPersist = query.from(MbProvision.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getProvisionName() != null && !StringUtils.isEmpty(searchModel.getProvisionName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("provisionName")),
					"%" + searchModel.getProvisionName().toUpperCase() + "%")));
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
	public Page<MbProvisionDto> searchMbProvision(SearchMbProvisionModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<MbProvision> query = cb.createQuery(MbProvision.class);
		Object[] queryObjs = this.createProvisionRootPersist(cb, query, searchModel);
		query.select((Root<MbProvision>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<MbProvision> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<MbProvision> objects = typedQuery.getResultList();
		List<MbProvisionDto> mbProvisionDtos = ModelMapperUtils.mapAll(objects, MbProvisionDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(MbProvision.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(mbProvisionDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public MbProvision update(CreateMbProvisionRequest request) {
		Optional<MbProvision> opt = mbProvisionDao.findById(request.getId());
		if (opt.isPresent()) {
			MbProvision save = mbProvisionDao.save(ModelMapperUtils.map(request, MbProvision.class));
			return ModelMapperUtils.map(save, MbProvision.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public MbProvision create(CreateMbProvisionRequest request) {
		Optional<MbProvision> opt = mbProvisionDao.findById(request.getId());
		if (opt.isPresent()) {
			return null;
		}
		MbProvision save = mbProvisionDao.save(ModelMapperUtils.map(request, MbProvision.class));
		return ModelMapperUtils.map(save, MbProvision.class);
	}

	@Override
	@Transactional(readOnly = false)
	public String delete(@RequestParam String id) {
		MbProvision mbProvision = new MbProvision();
		if (!id.isEmpty()) {
			Optional<MbProvision> opt = mbProvisionDao.findById(id);
			if (opt.isPresent()) {
				mbProvision = opt.get();
				mbProvision.setStatus("D");
				mbProvisionDao.save(mbProvision);
				return AppConstant.SUCCSESSFUL_CODE;
			}
		}
		return AppConstant.SYSTEM_ERORR_CODE;
	}

}
