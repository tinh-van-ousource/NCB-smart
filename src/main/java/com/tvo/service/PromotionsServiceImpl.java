package com.tvo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dao.PromotionsDAO;
import com.tvo.dto.CreatePromotionsDto;
import com.tvo.dto.PromotionsDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.Function;
import com.tvo.model.Promotions;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;

@Service
@Transactional
public class PromotionsServiceImpl implements PromotionsService {

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
        Root<Promotions> root = (Root<Promotions>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("proName")));

        TypedQuery<Promotions> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<Promotions> objects = typedQuery.getResultList();

        List<PromotionsDto> PromotionsDtos = ModelMapperUtils.mapAll(objects, PromotionsDto.class);
        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(Promotions.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(PromotionsDtos, pageable, total);
    }

    private Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchPromotion resource) {
        final Root<Promotions> rootPersist = query.from(Promotions.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getProCode() != null && !StringUtils.isEmpty(resource.getProCode().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("proCode")), "%" + resource.getProCode().toUpperCase() + "%")));
		}
        if (StringUtils.isNotBlank(resource.getProName())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("proName")), "%" + resource.getProName().toUpperCase() + "%")));
        }
        if (StringUtils.isNotBlank(resource.getStatus())) {
            predicates.add(cb.and(cb.like(rootPersist.get("status"),"%" + resource.getStatus()+ "%")));
        }
        // predicates.add(cb.and(rootPersist.get("prd").isNull()));

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public CreatePromotionsDto create(CreatePromotionsRequest request) {
    	Promotions promotions = promotionsDao.findByProCode(request.getProCode());
		if (promotions != null) {
			return null;
		}
		promotions = ModelMapperUtils.map(request, Promotions.class);
		promotions.setCreatedDate(DateTimeUtil.getNow());
		Promotions save = promotionsDao.save(promotions);
		return ModelMapperUtils.map(save, CreatePromotionsDto.class);
    }

	@Override
	public PromotionsDto update(UpdatePromotionRequest request) {
		Promotions opt = promotionsDao.findByProCode(request.getProCode());
		Date createDateOld = opt.getCreatedDate();
		if (opt != null) {
			Promotions promotion = ModelMapperUtils.map(request,Promotions.class);
			promotion.setCreatedDate(createDateOld);
			Promotions save = promotionsDao.save(promotion);
			return ModelMapperUtils.map(save, PromotionsDto.class);
		}
		return null;
	}

	@Override
	public Boolean delete(String proCode) {
		Promotions promotions = promotionsDao.findByProCode(proCode);
		if (promotions != null) {
//            promotions.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
            promotionsDao.delete(promotions);
            return true;
		}
        return false;
	}

	@Override
    public PromotionsDto detail(String proCode) {
        Promotions promotions = promotionsDao.findByProCode(proCode);
        if (promotions == null) {
            return null;
        }
        return ModelMapperUtils.map(promotions, PromotionsDto.class);
    }

    @Override
    public List<String> getDistinctByProCode() {
        return promotionsDao.getDistinctByProCode();
    }

}
	