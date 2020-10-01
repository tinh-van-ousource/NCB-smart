package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbGuidelineModel;
import com.tvo.dao.NcbGuidelineDao;
import com.tvo.dto.NcbGuidelineDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.NcbGuideline;
import com.tvo.request.CreateNcbGuidelineRequest;
import com.tvo.request.UpdateNcbGuidelineRequest;
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
public class NcbGuidelineServiceImpl implements NcbGuidelineService {
	@Autowired
	private NcbGuidelineDao ncbGuidelineDao;

	private final EntityManager entityManager;

	public NcbGuidelineServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NcbGuidelineDto> findAll() {
		return ModelMapperUtils.mapAll(ncbGuidelineDao.findAll(), NcbGuidelineDto.class);
	}

	@Override
	public NcbGuidelineDto findById(Long id) {
		NcbGuideline ncbGuideline = new NcbGuideline();
		Optional<NcbGuideline> opt = ncbGuidelineDao.findById(id);
		if (opt.isPresent()) {
			ncbGuideline = opt.get();
		}
		return ModelMapperUtils.map(ncbGuideline, NcbGuidelineDto.class);
	}

	@SuppressWarnings("unused")
	private Object[] createNcbGuidelineRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchNcbGuidelineModel searchModel) {
		final Root<NcbGuideline> rootPersist = query.from(NcbGuideline.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getServiceId() != null && !StringUtils.isEmpty(searchModel.getServiceId().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("serviceId")),
					"%" + searchModel.getServiceId().toUpperCase() + "%")));
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
	public Page<NcbGuidelineDto> searchNcbGuideline(SearchNcbGuidelineModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<NcbGuideline> query = cb.createQuery(NcbGuideline.class);
		Object[] queryObjs = createNcbGuidelineRootPersist(cb, query, searchModel);
		query.select((Root<NcbGuideline>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<NcbGuideline> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<NcbGuideline> objects = typedQuery.getResultList();
		List<NcbGuidelineDto> ncbGuidelineDtos = ModelMapperUtils.mapAll(objects, NcbGuidelineDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(NcbGuideline.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ncbGuidelineDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbGuidelineDto update(UpdateNcbGuidelineRequest request) {
		Optional<NcbGuideline> opt = ncbGuidelineDao.findById(request.getId());
		if (opt.isPresent()) {
			NcbGuideline ncbGuideline = ModelMapperUtils.map(request, NcbGuideline.class);
			ncbGuideline.setCreatedDate(opt.get().getCreatedDate());

			NcbGuideline save = ncbGuidelineDao.save(ncbGuideline);

			return ModelMapperUtils.map(save, NcbGuidelineDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public NcbGuidelineDto create(CreateNcbGuidelineRequest request) {
		NcbGuideline save = ncbGuidelineDao.save(ModelMapperUtils.map(request, NcbGuideline.class));
		save.setCreatedDate(LocalDateTime.now());
		save.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
		return ModelMapperUtils.map(save, NcbGuidelineDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
		NcbGuideline ncbGuideline = new NcbGuideline();
		if (id != null) {
			Optional<NcbGuideline> opt = ncbGuidelineDao.findById(id);
			if (opt.isPresent()) {
//				ncbGuideline = opt.get();
//				ncbGuideline.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//				ncbGuidelineDao.save(ncbGuideline);
				ncbGuidelineDao.deleteById(id);
				return true;
			}
		}
		return false;
	}

}
