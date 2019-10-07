package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dao.FunctionDAO;
import com.tvo.dao.ProductFeeDAO;
import com.tvo.dto.CityDto;
import com.tvo.dto.FunctionAndProductFeeDto;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.ProductFeeDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.City;
import com.tvo.model.Function;
import com.tvo.model.ProductFeeEntity;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.DeleteFunctionRequest;
import com.tvo.request.UpdateFunctionAndProductFeeRq;
import com.tvo.request.UpdateFunctionRequest;
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
	FunctionDAO functionDao;

	@Autowired
	private ProductFeeDAO productFeeDAO;

	@Override
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Function> query = 	cb.createQuery(Function.class);
		Object[] queryObjs = this.createFunctionRootPersist(cb, query, searchFunction);
		Root<Function> root = (Root<Function>) queryObjs[0];
        query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.desc(root.get("id")));
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
		final List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.and(rootPersist.get("prd").isNotNull()));

		if (resource.getStatus() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("status")), resource.getStatus().toUpperCase())));
		}
		if (resource.getPrd() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPrd().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("prd")), resource.getPrd().toUpperCase())));
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
	public FunctionDto delete(DeleteFunctionRequest deleteFunctionRequest) {
		Function function = functionDao.findByPrd(deleteFunctionRequest.getPrd());
		if (function == null) {
			return null;
		}
		function = ModelMapperUtils.map(deleteFunctionRequest, Function.class);
		function.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
		Function save = functionDao.save(function);
		return ModelMapperUtils.map(save, FunctionDto.class);
	}

	@Override
	public FunctionDto detail(String prd) {
		Function function = functionDao.findByPrd(prd);
        if (function == null) {
            return null;
        }
        return ModelMapperUtils.map(function, FunctionDto.class);
	}

	@Override
	public List<String> getAllPrdName() {
		return functionDao.getAllPrdName();
	}

	@Override
	public FunctionAndProductFeeDto searchFunctionAndProductFree(SearchFunction searchFunction) {

		Function function = functionDao.findByPrd(searchFunction.getPrd());
		FunctionDto functionDto = null;
		if (function != null) {
			functionDto = ModelMapperUtils.map(function, FunctionDto.class);
		}

		ProductFeeEntity productFeeEntity = productFeeDAO.findByGrprdId(searchFunction.getPrd());
		ProductFeeDto productFeeDto = null;
		if (productFeeEntity != null) {
			productFeeDto = ModelMapperUtils.map(productFeeEntity, ProductFeeDto.class);
		}
		return new FunctionAndProductFeeDto(functionDto, productFeeDto);
	}

	@Override
	@Transactional(readOnly = false)
	public FunctionAndProductFeeDto updatePopup(UpdateFunctionAndProductFeeRq functionAndProductFeeRq) {
		Optional<Function> optFunction = functionDao.findById(functionAndProductFeeRq.getFunction().getId());
		Optional<ProductFeeEntity> optProductFee = productFeeDAO.findById(functionAndProductFeeRq.getProductFee().getId());
		if (optFunction.isPresent() && optProductFee.isPresent()) {
			Function function = ModelMapperUtils.map(functionAndProductFeeRq.getFunction(), Function.class);
			Function saveFunction = functionDao.save(function);

			ProductFeeEntity productFeeEntity = ModelMapperUtils.map(functionAndProductFeeRq.getProductFee(), ProductFeeEntity.class);
			ProductFeeEntity saveProductFeeEntity = productFeeDAO.save(productFeeEntity);

			FunctionDto functionDto = ModelMapperUtils.map(saveFunction, FunctionDto.class);
			ProductFeeDto productFeeDto = ModelMapperUtils.map(saveProductFeeEntity, ProductFeeDto.class);
			return ModelMapperUtils.map(new FunctionAndProductFeeDto(functionDto, productFeeDto), FunctionAndProductFeeDto.class);
		}
		return null;
	}
}
