package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchPromotion;
import com.tvo.dao.PromotionsDAO;
import com.tvo.dto.PromotionsDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.Function;
import com.tvo.model.ParCardProductEntity;
import com.tvo.model.Promotions;
import com.tvo.request.CreatePromotionsRequest;
import com.tvo.request.UpdatePromotionRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.desc(root.get("id")));
        query.orderBy(cb.desc(root.get("id")));

        TypedQuery<Promotions> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
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

    private Object[] createFunctionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchPromotion resource) {
        final Root<Function> rootPersist = query.from(Function.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(resource.getPromotionName())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("promotionName")),
                    "%" + resource.getPromotionName().toUpperCase() + "%")));
        }

        if (StringUtils.isNotBlank(resource.getStatus())) {
            predicates.add(cb.and(cb.equal(rootPersist.get("status"),
                    resource.getStatus())));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public List<PromotionsDto> create(CreatePromotionsRequest request) {
        Date now = DateTimeUtil.getNow();
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<Promotions> promotionsList = new ArrayList<>();
        Promotions promotionBase = ModelMapperUtils.map(request, Promotions.class);
        promotionBase.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        promotionBase.setCreatedDate(now);
        promotionBase.setCreatedBy(currentUserName);

        Promotions promotions1 = ModelMapperUtils.map(promotionBase, new Promotions());
        promotions1.setTypeId("QLTK");
        promotions1.setPercentage(request.getPercentage1());
        promotions1.setFromDate(request.getFromDate1());
        promotions1.setToDate(request.getToDate1());
        promotionsList.add(promotions1);

        Promotions promotions2 = ModelMapperUtils.map(promotionBase, new Promotions());
        promotions2.setTypeId("CK");
        promotions2.setTranType("URT");
        promotions2.setPercentage(request.getPercentage2());
        promotions2.setFromDate(request.getFromDate2());
        promotions2.setToDate(request.getToDate2());
        promotionsList.add(promotions2);

        Promotions promotions3 = ModelMapperUtils.map(promotionBase, new Promotions());
        promotions3.setTypeId("CK");
        promotions3.setTranType("ISL");
        promotions3.setPercentage(request.getPercentage3());
        promotions3.setFromDate(request.getFromDate3());
        promotions3.setToDate(request.getToDate3());
        promotionsList.add(promotions3);

        Promotions promotions4 = ModelMapperUtils.map(promotionBase, new Promotions());
        promotions4.setTypeId("CK");
        promotions4.setTranType("IBT");
        promotions4.setPercentage(request.getPercentage4());
        promotions4.setFromDate(request.getFromDate4());
        promotions4.setToDate(request.getToDate4());
        promotionsList.add(promotions4);

        return ModelMapperUtils.mapAll(promotionsDao.saveAll(promotionsList), PromotionsDto.class);
    }

    @Override
    @Transactional
    public PromotionsDto update(UpdatePromotionRequest request) {
        Optional<Promotions> promotionBase = promotionsDao.findById(request.getId());
        if (promotionBase.isPresent()) {
            Promotions promotions = ModelMapperUtils.map(request, Promotions.class);
            return ModelMapperUtils.map(promotionsDao.save(promotions), PromotionsDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        Optional<Promotions> promotionBase = promotionsDao.findById(id);
        if (promotionBase.isPresent()) {
            Promotions promotions = promotionBase.get();
            promotions.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
            return true;
        }
        return false;
    }

    @Override
    public PromotionsDto detail(Long id) {
        Optional<Promotions> promotionBase = promotionsDao.findById(id);
        return promotionBase.map(promotions -> ModelMapperUtils.map(promotions, PromotionsDto.class)).orElse(null);
    }

}
	