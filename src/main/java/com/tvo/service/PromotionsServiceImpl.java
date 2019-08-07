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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchPromotion;
import com.tvo.dao.FunctionDAO;
import com.tvo.dao.PromotionsDAO;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.model.Function;
import com.tvo.model.Promotions;
import com.tvo.request.CreatePromotionsRequest;

@Service
@Transactional
public class PromotionsServiceImpl implements PromotionsService{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	PromotionsDAO promotionsDao;
	
	@Override
	public Page<PromotionsDto> searchPromotion(com.tvo.controllerDto.searchPromotion searchPromotion,Pageable pageable) {
			final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
	final CriteriaQuery<Promotions> query = cb.createQuery(Promotions.class);
	Object[] queryObjs = this.createFunctionRootPersist(cb, query, searchPromotion);
	query.select((Root<Promotions>) queryObjs[0]);
	query.where((Predicate[]) queryObjs[1]);
	TypedQuery<Promotions> typedQuery = this.entityManager.createQuery(query);
	
	typedQuery.setFirstResult((int)pageable.getOffset());
    typedQuery.setMaxResults(pageable.getPageSize());
    final List<Promotions> objects = typedQuery.getResultList();
	List<PromotionsDto> PromotionsDtos = ModelMapperUtils.mapAll(objects, PromotionsDto.class);

	
	final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    countQuery.select(cbTotal.count(countQuery.from(Function.class)));
    countQuery.where((Predicate[]) queryObjs[1]);
    Long total = entityManager.createQuery(countQuery).getSingleResult();
	return new PageImpl<>(PromotionsDtos, pageable, total);
	}
		
	public Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchPromotion resource) {
		final Root<Function> rootPersist = query.from(Function.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		
		if (resource.getType() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getType().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("typyFunction"), resource.getType())));
		}

		if (resource.getFunctionType()!= null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFunctionType().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("function"), resource.getFunctionType())));
		}

		if (resource.getCuType() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCuType().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("cuType"), resource.getCuType())));
		}
		
		
	

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public PromotionsDto createPromotions(CreatePromotionsRequest request) {
		Promotions promotions = promotionsDao.findByType(request.getType());
		if (promotions != null) {
			return null;
		}
		promotions = ModelMapperUtils.map(request, Promotions.class);
		promotions.setPromotion(request.getPromotion());
		promotions.setPromotionName(request.getPromotionName());
		promotions.setCuType(request.getCuType());
		promotions.setType(request.getType());
		promotions.setPercentage(request.getPercentage());
		promotions.setFunctionType(request.getFunctionType());
		promotions.setFromDate(request.getFromDate());
		promotions.setToDate(request.getToDate());
		Promotions save = promotionsDao.save(promotions);
		return ModelMapperUtils.map(save, PromotionsDto.class);
	}

	@Override
	public PromotionsDto update(PromotionsDto promotionsDto) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
	