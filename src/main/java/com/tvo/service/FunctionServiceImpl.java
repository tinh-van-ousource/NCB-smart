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

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dao.FunctionDAO;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.NotifyDto;
import com.tvo.model.Function;
import com.tvo.model.Notify;
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

		if (resource.getTypeFunction() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getTypeFunction().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("typyFunction"), resource.getTypeFunction())));
		}

		if (resource.getFunction() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFunction().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("function"), resource.getFunction())));
		}

		if (resource.getStatus() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
		}
		if (resource.getQuantity() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getQuantity().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("quantity"), resource.getQuantity())));
		}
		if (resource.getCuType() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCuType().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("cuType"), resource.getCuType())));
		}
		if (resource.getCcy() != null && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCcy().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("ccy"), resource.getCcy())));
		}
		if (resource.getLimitDaily() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getLimitDaily().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("limitDaily"), resource.getLimitDaily())));
		}
		if (resource.getLimitFace() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getLimitFace().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("limitDay"), resource.getLimitFace())));
		}
		if (resource.getUserId() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFunction().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("function"), resource.getFunction())));
		}
		if (resource.getMin() != null && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getMin().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("min"), resource.getMin())));
		}
		if (resource.getMax() != null && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getMax().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("max"), resource.getMax())));
		}

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public FunctionDto create(CreateFunctionRequest request) {
		Function function = functionDao.findByTypeId(request.getTypeId());
		if (function != null) {
			return null;
		}
		function = ModelMapperUtils.map(request, Function.class);
		function.setTypeId(request.getTypeId());
		function.setTranType(request.getTranType());
		function.setQuantity(request.getQuantity());
		function.setPromotion(request.getPromotion());
		function.setPromotionName(request.getPromotionName());
		function.setCreatedBy(request.getCreatedBy());
		function.setCreatedDate(request.getCreatedDate());
		function.setCustomerType(request.getCustomerType());
		function.setFromDate(request.getFromDate());
		function.setStatus(request.getStatus());
		function.setToDate(request.getToDate());
		function.setPrdName(request.getPrdName());
		function.setPrd(request.getPrd());
		function.setPercentage(request.getPercentage());
		function.setMin(request.getMin());
		function.setMax(request.getMax());
		function.setLimitDaily(request.getLimitDaily());
		function.setLimitFaceid(request.getLimitFaceid());
		function.setLimitFinger(request.getLimitFinger());
		function.setId(request.getId());
		function.setCcy(request.getCcy());
		
		Function save = functionDao.save(function);
		return ModelMapperUtils.map(save, FunctionDto.class);
	}


	@Override
	@Transactional(readOnly = false)
	public FunctionDto update(UpdateFunctionRequest request) {
		Optional<Function> opt = functionDao.findById(request.getTypeId());
		if (opt.isPresent()) {
			Function function = ModelMapperUtils.map(request,Function.class);
			function.setTypeId(opt.get().getTypeId());
			function.setTranType(opt.get().getTranType());
			function.setQuantity(opt.get().getQuantity());
			function.setPromotion(opt.get().getPromotion());
			function.setPromotionName(opt.get().getPromotionName());
			function.setCreatedBy(opt.get().getCreatedBy());
			function.setCreatedDate(opt.get().getCreatedDate());
			function.setCustomerType(opt.get().getCustomerType());
			function.setFromDate(opt.get().getFromDate());
			function.setStatus(opt.get().getStatus());
			function.setToDate(opt.get().getToDate());
			function.setPrdName(opt.get().getPrdName());
			function.setPrd(opt.get().getPrd());
			function.setPercentage(opt.get().getPercentage());
			function.setMin(opt.get().getMin());
			function.setMax(opt.get().getMax());
			function.setLimitDaily(opt.get().getLimitDaily());
			function.setLimitFaceid(opt.get().getLimitFaceid());
			function.setLimitFinger(opt.get().getLimitFinger());
			function.setId(opt.get().getId());
			function.setCcy(opt.get().getCcy());
			
			Function save = functionDao.save(function);

			return ModelMapperUtils.map(save, FunctionDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
		Function function = new Function();
		if (id != null) {
			Optional<Function> opt = functionDao.findById(String.valueOf(id));
			if (opt.isPresent()) {
				function = opt.get();
				function.setTypeId(" ");
				function.setTranType(" ");
				function.setStatus(" ");
				function.setQuantity(" ");
				function.setPromotionName(" ");
				function.setPromotion(" ");
				function.setPrdName(" ");
				function.setCcy(" ");
				functionDao.save(function);
				return true;
			}
		}
		return false;
	}
}
