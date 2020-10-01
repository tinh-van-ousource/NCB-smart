package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dao.FunctionDAO;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.ProductDropListDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.Function;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.FunctionRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FunctionDAO functionDao;

	@Override
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Function> query = cb.createQuery(Function.class);
		Object[] queryObjs = this.createFunctionRootPersist(cb, query, searchFunction);
		Root<Function> root = (Root<Function>) queryObjs[0];
        query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.asc(root.get("prdName")));
		TypedQuery<Function> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<Function> objects = typedQuery.getResultList();
		List<FunctionDto> functionDtos = ModelMapperUtils.mapAll(objects, FunctionDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(Function.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(functionDtos, pageable, total);
	}

	public Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,  SearchFunction resource) {
		final Root<Function> rootPersist = query.from(Function.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.and(rootPersist.get("prd").isNotNull()));

		if (resource.getStatus() != null && !StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("status")),resource.getStatus().toUpperCase())));
		}
		if (resource.getPrd() != null && !StringUtils.isEmpty(resource.getPrd().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("prd")), "%" + resource.getPrd().toUpperCase() + "%")));
		}
		if (resource.getTranType() != null && !StringUtils.isEmpty(resource.getTranType().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("tranType")), resource.getTranType().toUpperCase())));
		}
		if (resource.getTypeId() != null && !StringUtils.isEmpty(resource.getTypeId().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("typeId")),resource.getTypeId().toUpperCase())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public CreateFunctionDto create(CreateFunctionRequest request) {
		Function function = functionDao.findByPrdAndPrdNameAndTranType(request.getPrd(), request.getPrdName(), request.getTranType());
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
	public FunctionDto update(FunctionRequest request) {
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
	public FunctionDto delete(Long functionId) {
		Optional<Function> opt = functionDao.findById(functionId);
		if (opt.isPresent()) {
//			Function function = opt.get();
//			function.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//			Function save = functionDao.save(function);
			functionDao.deleteById(functionId);
			return ModelMapperUtils.map(new Function(), FunctionDto.class);
		}
		return null;
	}

	@Override
	public FunctionDto detail(Long functionId) {
		Optional<Function> optFunction = functionDao.findById(functionId);
        if (optFunction.isPresent()) {
			return ModelMapperUtils.map(optFunction.get(), FunctionDto.class);
        }
		return null;
	}

	@Override
	public List<String> getAllPrdName() {
		return functionDao.getAllPrdName();
	}

	@Override
	public List<ProductDropListDto> getProductDropList() {
		List<ProductDropListDto> productDropListDtos = new ArrayList<>();
		List<Object> listComp = functionDao.getAllPrdAndPrdName();
		for (Object depart : listComp) {
			Object[] departs = (Object[]) depart;
			productDropListDtos.add(
					new ProductDropListDto(departs[0].toString(), departs[1].toString()));
		}
		return productDropListDtos;
	}

}
