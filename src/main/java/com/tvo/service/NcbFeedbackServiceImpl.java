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
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dao.NcbFeedbackDao;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.model.NcbFeedback;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;

@Service
public class NcbFeedbackServiceImpl implements NcbFeedbackService {

	@Autowired
	private NcbFeedbackDao ncbFeedbackDao;

	private final EntityManager entityManager;

	public NcbFeedbackServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NcbFeedbackDto> findAll() {
		return ModelMapperUtils.mapAll(ncbFeedbackDao.findAll(), NcbFeedbackDto.class);
	}

	@Override
	public NcbFeedbackDto findById(Long id) {
		NcbFeedback ncbFeedback = new NcbFeedback();
		try {
			Optional<NcbFeedback> opt = ncbFeedbackDao.findById(id);
			if (opt.isPresent()) {
				ncbFeedback = opt.get();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return ModelMapperUtils.map(ncbFeedback, NcbFeedbackDto.class);
	}

	@SuppressWarnings("unused")
	private Object[] createNcbFeedbackRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchNcbFeedbackModel searchModel) {
		final Root<NcbFeedback> rootPersist = query.from(NcbFeedback.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getProductCode() != null && !StringUtils.isEmpty(searchModel.getProductCode().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("productCode")),
					searchModel.getProductCode().toUpperCase())));
		}
		if (searchModel.getProductName() != null && !StringUtils.isEmpty(searchModel.getProductName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("productName")),
					searchModel.getProductName().toUpperCase())));
		}
		if (searchModel.getType() != null && !StringUtils.isEmpty(searchModel.getType().trim())) {
			predicates.add(
					cb.and(cb.equal(cb.upper(rootPersist.<String>get("type")), searchModel.getType().toUpperCase())));
		}
		if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), searchModel.getStatus())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<NcbFeedbackDto> searchNcbFeedback(SearchNcbFeedbackModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<NcbFeedback> query = cb.createQuery(NcbFeedback.class);
		Object[] queryObjs = createNcbFeedbackRootPersist(cb, query, searchModel);
		query.select((Root<NcbFeedback>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<NcbFeedback> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<NcbFeedback> objects = typedQuery.getResultList();
		List<NcbFeedbackDto> ncbFeedbackDtos = ModelMapperUtils.mapAll(objects, NcbFeedbackDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(NcbFeedback.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ncbFeedbackDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbFeedbackDto create(CreateNcbFeedbackRequest request) {
		NcbFeedback save = ncbFeedbackDao.save(ModelMapperUtils.map(request, NcbFeedback.class));
		save.setCreatedDate(LocalDateTime.now());
		save.setStatus("A");
		return ModelMapperUtils.map(save, NcbFeedbackDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbFeedbackDto update(UpdateNcbFeedbackRequest request) {
		Optional<NcbFeedback> opt = ncbFeedbackDao.findById(request.getId());
		if (opt.isPresent()) {
			NcbFeedback ncbFeedback = ModelMapperUtils.map(request, NcbFeedback.class);
			ncbFeedback.setCreatedDate(opt.get().getCreatedDate());

			NcbFeedback save = ncbFeedbackDao.save(ncbFeedback);

			return ModelMapperUtils.map(save, NcbFeedbackDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public String delete(Long id) {
		NcbFeedback ncbFeedback = new NcbFeedback();
		if (id != null) {
			Optional<NcbFeedback> opt = ncbFeedbackDao.findById(id);
			if (opt.isPresent()) {
				ncbFeedback = opt.get();
				ncbFeedback.setStatus("D");
				ncbFeedbackDao.save(ncbFeedback);
				return AppConstant.SUCCSESSFUL_CODE;
			}
		}
		return AppConstant.SYSTEM_ERORR_CODE;
	}

}
