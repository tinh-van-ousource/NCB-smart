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
		
		

		if (resource.getPromotionName()!= null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPromotionName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("promotionName"), resource.getPromotionName())));
		}


		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public PromotionsDto create(CreatePromotionsRequest request) {
		Promotions promotions = promotionsDao.findByid(request.getId());
		if (promotions != null) {
			return null;
		}
		promotions = ModelMapperUtils.map(request, Promotions.class);
		
		Promotions save = promotionsDao.save(promotions);
		return ModelMapperUtils.map(save, PromotionsDto.class);
	}


	@Override
	@Transactional(readOnly = false)
	public PromotionsDto update(UpdatePromotionRequest request) {
		Promotions opt = promotionsDao.findByid(request.getId());
		if (opt != null) {
			Promotions promotions = ModelMapperUtils.map(request,Promotions.class);
			
			Promotions save = promotionsDao.save(promotions);


			return ModelMapperUtils.map(save, PromotionsDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
		Promotions function = promotionsDao.findByid(id);
		if (id != null) {
			promotionsDao.delete(function);
			return true;
		}
		return false;
	}

	@Override
	public PromotionsDto detail(Long id) {
		 Promotions promotions = promotionsDao.findByid(id);
	        if (promotions == null) {
	            return null;
	        }
	        return ModelMapperUtils.map(promotions, PromotionsDto.class);
	}

	
	
}
	