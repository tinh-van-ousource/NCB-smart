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
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dao.FunctionDAO;
import com.tvo.dto.FunctionDto;
import com.tvo.model.Function;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.UpdateFunctionRequest;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	FunctionDAO functionDao;

	@Override
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Function> query = cb.createQuery(Function.class);
		Object[] queryObjs = this.createFunctionRootPersist(cb, query, searchFunction);
		query.select((Root<Function>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<Function> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<Function> objects = typedQuery.getResultList();
		List<FunctionDto> FunctionDtos = ModelMapperUtils.mapAll(objects, FunctionDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(Function.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(FunctionDtos, pageable, total);
	}

	public Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchFunction resource) {
		final Root<Function> rootPersist = query.from(Function.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);


		if (resource.getId() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getId().toString().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<Long>get("id"), resource.getId())));
//			predicates.add(cb.and(cb.like(cb.equal(rootPersist.<Long>get("getId")), resource.getId())));
		}
		
		if (resource.getPrdName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPrdName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("prdName")), resource.getPrdName().toUpperCase())));
		}

		if (resource.getTranType() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTranType().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("tranType")), resource.getTranType().toUpperCase())));
		}

		if (resource.getTypeId() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTypeId().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("typeId")), resource.getTypeId().toUpperCase())));
		}



		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public CreateFunctionDto create(CreateFunctionRequest request) {
		Function function = functionDao.findByPrd(request.getPrd());
		if (function != null) {
			return null;
		}
		function = ModelMapperUtils.map(request, Function.class);
		function.setCreatedDate(DateTimeUtil.getNow());
		
		
		Function save = functionDao.save(function);
		return ModelMapperUtils.map(save, CreateFunctionDto.class);
	}


	@Override
	@Transactional(readOnly = false)
	public FunctionDto update(UpdateFunctionRequest request) {
		Optional<Function> opt = functionDao.findById(request.getId());
		if (opt.isPresent()) {
			Function function = ModelMapperUtils.map(request,Function.class);
			
			Function save = functionDao.save(function);

			return ModelMapperUtils.map(save, FunctionDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(String prd) {
		Function function = functionDao.findByPrd(prd);
		if (prd != null) {
			functionDao.delete(function);
			return true;
		}
		return false;
	}

	@Override
	public FunctionDto detail(String prdName) {
		Function function = functionDao.findByPrdName(prdName);
        if (function == null) {
            return null;
        }
        return ModelMapperUtils.map(function, FunctionDto.class);
	}

	@Override
	public List<String> getAllPrdName() {
		return functionDao.getAllPrdName();
	}
}
