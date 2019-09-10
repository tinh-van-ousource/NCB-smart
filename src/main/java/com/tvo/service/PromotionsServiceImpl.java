package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dao.PromotionsDAO;
import com.tvo.dto.NotifyDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.model.Function;
import com.tvo.model.Notify;
import com.tvo.model.Promotions;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;

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
public class PromotionsServiceImpl implements PromotionsService{
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	PromotionsDAO promotionsDao;
	
	@Override
	public Page<PromotionsDto> searchPromotion(SearchPromotion searchPromotion, Pageable pageable) {
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
		
	public Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchPromotion resource) {
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
	public PromotionsDto create(CreatePromotionsRequest request) {
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
	@Transactional(readOnly = false)
	public PromotionsDto update(UpdatePromotionRequest request) {
		Optional<Promotions> opt = promotionsDao.findById(request.getType());
		if (opt.isPresent()) {
			Promotions promotions = ModelMapperUtils.map(request,Promotions.class);
			promotions.setPromotion(opt.get().getPromotion());
			promotions.setPromotionName(opt.get().getPromotionName());
			promotions.setCuType(opt.get().getCuType());
			promotions.setType(opt.get().getType());
			promotions.setPercentage(opt.get().getPercentage());
			promotions.setFunctionType(opt.get().getFunctionType());
			promotions.setFromDate(opt.get().getFromDate());
			promotions.setToDate(opt.get().getToDate());
			Promotions save = promotionsDao.save(promotions);


			return ModelMapperUtils.map(save, PromotionsDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
		Promotions promotions = new Promotions();
		if (id != null) {
			Optional<Promotions> opt = promotionsDao.findById(String.valueOf(id));
			if (opt.isPresent()) {
				promotions = opt.get();
				promotions.setType(" ");
				promotionsDao.save(promotions);
				return true;
			}
		}
		return false;
	}	
	
}
	