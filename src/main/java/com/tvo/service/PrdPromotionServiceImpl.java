package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchPrdPromotion;
import com.tvo.dao.PrdPromotionMbAppDAO;
import com.tvo.dto.CreatePrdPromotionRqDto;
import com.tvo.dto.PrdPromotionDto;
import com.tvo.dto.PrdPromotionRq;
import com.tvo.enums.StatusActivate;
import com.tvo.model.PrdPromotionMbApp;
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
public class PrdPromotionServiceImpl implements PrdPromotionService {

    @Autowired
    private PrdPromotionMbAppDAO prdPromotionMbAppDAO;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<PrdPromotionDto> search(SearchPrdPromotion searchPrdPromotion, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<PrdPromotionMbApp> query = cb.createQuery(PrdPromotionMbApp.class);
        Object[] queryObjs = this.createPrdPromotionMbAppRootPersist(cb, query, searchPrdPromotion);
        Root<PrdPromotionMbApp> root = (Root<PrdPromotionMbApp>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("prd")));
        TypedQuery<PrdPromotionMbApp> typedQuery = this.entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<PrdPromotionMbApp> objects = typedQuery.getResultList();
        List<PrdPromotionDto> functionDtos = ModelMapperUtils.mapAll(objects, PrdPromotionDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(PrdPromotionMbApp.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(functionDtos, pageable, total);
    }

    @Override
    public PrdPromotionDto detail(Long prdPromotionId) {
        Optional<PrdPromotionMbApp> optProductFee = prdPromotionMbAppDAO.findById(prdPromotionId);
        if (optProductFee.isPresent()) {
            return ModelMapperUtils.map(optProductFee.get(), PrdPromotionDto.class);
        }
        return null;
    }

    @Override
    public PrdPromotionDto create(CreatePrdPromotionRqDto createPrdPromotionRqDto) {
        List<PrdPromotionMbApp> prdPromotionMbApps = prdPromotionMbAppDAO.findByPrdAndAndProCode(createPrdPromotionRqDto.getPrd(), createPrdPromotionRqDto.getProCode());
        if (prdPromotionMbApps.isEmpty()) {
            PrdPromotionMbApp productFeeEntity = ModelMapperUtils.map(createPrdPromotionRqDto, PrdPromotionMbApp.class);
            productFeeEntity.setCreatedDate(DateTimeUtil.getNow());
            PrdPromotionMbApp saveProductFee = prdPromotionMbAppDAO.save(productFeeEntity);
            PrdPromotionDto productFeeDto = ModelMapperUtils.map(saveProductFee, PrdPromotionDto.class);
            return ModelMapperUtils.map(productFeeDto, PrdPromotionDto.class);
        }
        return null;
    }

    @Override
    public PrdPromotionDto update(PrdPromotionRq prdPromotionRq) {
        Optional<PrdPromotionMbApp> optPrdPromotion = prdPromotionMbAppDAO.findById(prdPromotionRq.getId());
        if (optPrdPromotion.isPresent()) {
            PrdPromotionMbApp prdPromotionMbApp = ModelMapperUtils.map(prdPromotionRq, PrdPromotionMbApp.class);
            prdPromotionMbApp.setCreatedDate(optPrdPromotion.get().getCreatedDate());
            PrdPromotionMbApp saveProductFeeEntity = prdPromotionMbAppDAO.save(prdPromotionMbApp);
            PrdPromotionDto prdPromotionDto = ModelMapperUtils.map(saveProductFeeEntity, PrdPromotionDto.class);
            return ModelMapperUtils.map(prdPromotionDto, PrdPromotionDto.class);
        }
        return null;
    }

    @Override
    public Boolean delete(Long prdPromotionId) {
        Optional<PrdPromotionMbApp> opt = prdPromotionMbAppDAO.findById(prdPromotionId);
        if (opt.isPresent()) {
//            PrdPromotionMbApp prdPromotionMbApp = opt.get();
//            prdPromotionMbApp.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//            prdPromotionMbAppDAO.save(prdPromotionMbApp);
            prdPromotionMbAppDAO.delete(opt.get());
            return true;
        }
        return false;
    }

    private Object[] createPrdPromotionMbAppRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchPrdPromotion resource) {
        final Root<PrdPromotionMbApp> rootPersist = query.from(PrdPromotionMbApp.class);
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.and(rootPersist.get("prd").isNotNull()));

        if (resource.getPrd() != null && !StringUtils.isEmpty(resource.getPrd().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("prd")), "%" +resource.getPrd().toUpperCase()+ "%")));
        }
        if (resource.getProCode() != null && !StringUtils.isEmpty(resource.getProCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("proCode")),"%" + resource.getProCode().toUpperCase()+ "%")));
        }
        if (resource.getProCode() != null && !StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("status")),"%" + resource.getStatus().toUpperCase()+ "%")));
        }
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }
}
