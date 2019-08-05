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
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchFunction;
import com.tvo.dao.BankTransferDAO;
import com.tvo.dao.FunctionDAO;
import com.tvo.dto.BankTransferDto;
import com.tvo.dto.FunctionDto;
import com.tvo.model.BankTransfer;
import com.tvo.model.City;
import com.tvo.model.Function;
import com.tvo.model.ParCardProduct;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.request.CreateFunctionRequest;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	FunctionDAO functionDao;
	
	@Override
	public Page<FunctionDto> searchFunction(searchFunction searchFunction, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Function> query = cb.createQuery(Function.class);
		Object[] queryObjs = this.createFunctionRootPersist(cb, query, searchFunction);
		query.select((Root<Function>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<Function> typedQuery = this.entityManager.createQuery(query);
		
		typedQuery.setFirstResult((int)pageable.getOffset());
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
	public Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchFunction resource) {
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
		if (resource.getCcy() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCcy().trim())) {
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
		if (resource.getMin() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getMin().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("min"), resource.getMin())));
		}
		if (resource.getMax() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getMax().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("max"), resource.getMax())));
		}
		
	

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}
	@Override
	public FunctionDto createFunction(CreateFunctionRequest request) {
		Function function = functionDao.findByTypeFunction(request.getTypeFunction());
		if (function != null) {
			return null;
		}
		function = ModelMapperUtils.map(request, Function.class);
		function.setUserId(request.getUserId());
		function.setId(request.getId());
		function.setCuType(request.getCuType());
		function.setTypeFunction(request.getTypeFunction());
		function.setIdName(request.getIdName());
		function.setQuantity(request.getQuantity());
		function.setStatus(request.getStatus());
		function.setFunction(request.getFunction());
		function.setLimitDaily(request.getLimitDaily());
		function.setLimitFace(request.getLimitFace());
		function.setMax(request.getMax());
		function.setMin(request.getMin());
		function.setCreateDate(request.getCreateDate());
		function.setCcy(request.getCcy());
		Function save = functionDao.save(function);
		return ModelMapperUtils.map(save, FunctionDto.class);
	}
	public String delete(String typeFunction) {
		if(!typeFunction.isEmpty()) {
			Function function = functionDao.findByTypeFunction(typeFunction);
			function.setStatus("DEACTIVE");
			functionDao.saveAndFlush(function);
			return AppConstant.SUCCSESSFUL_CODE;
		}
		return AppConstant.SYSTEM_ERORR_CODE;
	}
}
