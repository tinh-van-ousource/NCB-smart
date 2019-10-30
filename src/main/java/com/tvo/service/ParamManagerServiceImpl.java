package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dao.ParamManagerDao;
import com.tvo.dto.ParamManagerDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.request.UpdateParamManagerRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ParamManagerServiceImpl implements ParamManagerService {

	@Autowired
	private ParamManagerDao paramManagerDao;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<ParamManagerDto> findAll() {
		List<ParamManagerDto> listResult = ModelMapperUtils.mapAll(paramManagerDao.findAll(), ParamManagerDto.class);
		return listResult;
	}

	@Override
	public ParamManager findByParamNo(String paramNo) {
		ParamManager paramManager = paramManagerDao.findByParamNo(paramNo);
		if (paramManager == null) {
			return new ParamManager();
		}
		return paramManager;
	}

	public Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchParamManagerModel searchModel) {
		final Root<ParamManager> rootPersist = query.from(ParamManager.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getParamNo() != null && !StringUtils.isEmpty(searchModel.getParamNo().trim())) {
			predicates.add(cb.and(
					cb.equal(cb.upper(rootPersist.<String>get("paramNo")), searchModel.getParamNo().toUpperCase())));
		}
		if (searchModel.getParamName() != null && !StringUtils.isEmpty(searchModel.getParamName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("paramName")),"%" + searchModel.getParamName().toUpperCase() + "%")));
		}
		if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
			predicates.add(cb
					.and(cb.equal(cb.upper(rootPersist.<String>get("status")), searchModel.getStatus().toUpperCase())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<ParamManagerDto> searchParamManager(SearchParamManagerModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<ParamManager> query = cb.createQuery(ParamManager.class);
		Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel);
		Root<ParamManager> root = (Root<ParamManager>) queryObjs[0];
		query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.desc(root.get("paramNo")));

		TypedQuery<ParamManager> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<ParamManager> objects = typedQuery.getResultList();
		List<ParamManagerDto> paramManagerDtos = ModelMapperUtils.mapAll(objects, ParamManagerDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(ParamManager.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(paramManagerDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public ParamManager update(UpdateParamManagerRequest request) {
		ParamManager paramManager = paramManagerDao.findByParamNo(request.getParamNo());
		if (!ObjectUtils.isEmpty(paramManager)) {
			ParamManager save = paramManagerDao.save(ModelMapperUtils.map(request, ParamManager.class));
			return ModelMapperUtils.map(save, ParamManager.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public ParamManager create(CreateParamManagerRequest request) {
		ParamManager findByParamNo = paramManagerDao.findByParamNo(request.getParamNo());
		if (!ObjectUtils.isEmpty(findByParamNo)) {
			return null;
		}
		ParamManager paramManager = ModelMapperUtils.map(request, ParamManager.class);
		paramManager.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
		return ModelMapperUtils.map(paramManagerDao.save(paramManager), ParamManager.class);
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(String paramNo) {
		if (!paramNo.isEmpty()) {
			ParamManager paramManager = paramManagerDao.findByParamNo(paramNo);
			paramManager.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
			paramManagerDao.save(paramManager);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public List<ParamManager> saveAll(List<ParamManagerDto> paramManagerDtos) {
		List<ParamManager> paramManagers = ModelMapperUtils.mapAll(paramManagerDtos, ParamManager.class);
		return paramManagerDao.saveAll(paramManagers);
	}

}
