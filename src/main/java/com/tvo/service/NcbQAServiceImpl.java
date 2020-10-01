package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbQAModel;
import com.tvo.dao.NcbQADao;
import com.tvo.dto.NcbQADto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.NcbQA;
import com.tvo.request.CreateNcbQARequest;
import com.tvo.request.UpdateNcbQARequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NcbQAServiceImpl implements NcbQAService {

	@Autowired
	private NcbQADao ncbQADao;

	private final EntityManager entityManager;

	public NcbQAServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NcbQADto> findAll() {
		return ModelMapperUtils.mapAll(ncbQADao.findAll(), NcbQADto.class);
	}

	@Override
	public NcbQADto findById(Long id) {
		NcbQA ncbQA = new NcbQA();
		Optional<NcbQA> opt = ncbQADao.findById(id);
		if (opt.isPresent()) {
			ncbQA = opt.get();
		}
		return ModelMapperUtils.map(ncbQA, NcbQADto.class);
	}

	@SuppressWarnings("unused")
	private Object[] createNcbQARootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchNcbQAModel searchModel) {
		final Root<NcbQA> rootPersist = query.from(NcbQA.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getProductCode() != null && !StringUtils.isEmpty(searchModel.getProductCode().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("productCode")),
					"%" + searchModel.getProductCode().toUpperCase() + "%")));
		}
		if (searchModel.getProductName() != null && !StringUtils.isEmpty(searchModel.getProductName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("productName")),
					"%" + searchModel.getProductName().toUpperCase() + "%")));
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
	public Page<NcbQADto> searchNcbQA(SearchNcbQAModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<NcbQA> query = cb.createQuery(NcbQA.class);
		Object[] queryObjs = createNcbQARootPersist(cb, query, searchModel);
		query.select((Root<NcbQA>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<NcbQA> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<NcbQA> objects = typedQuery.getResultList();
		List<NcbQADto> ncbQADtos = ModelMapperUtils.mapAll(objects, NcbQADto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(NcbQA.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ncbQADtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbQADto create(CreateNcbQARequest request) {
		NcbQA ncbQA = ModelMapperUtils.map(request, NcbQA.class);
		ncbQA.setCreatedDate(LocalDateTime.now());
		ncbQA.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
		return ModelMapperUtils.map(ncbQADao.save(ncbQA), NcbQADto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbQADto update(UpdateNcbQARequest request) {
		Optional<NcbQA> opt = ncbQADao.findById(request.getId());
		if (opt.isPresent()) {
			NcbQA ncbQA = ModelMapperUtils.map(request, NcbQA.class);
			ncbQA.setCreatedDate(opt.get().getCreatedDate());

			NcbQA save = ncbQADao.save(ncbQA);

			return ModelMapperUtils.map(save, NcbQADto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
//		NcbQA ncbQA = new NcbQA();
//		if (id != null) {
			Optional<NcbQA> opt = ncbQADao.findById(id);
			if (opt.isPresent()) {
//				ncbQA = opt.get();
//				ncbQA.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//				ncbQADao.save(ncbQA);
				ncbQADao.deleteById(id);
				return true;
			}
//		}
		return false;
	}

}
