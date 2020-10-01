package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dao.MbProvisionDao;
import com.tvo.dto.MbProvisionResDto;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.MbProvision;
import com.tvo.model.ParCardPiture;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MbProvisionServiceImpl implements MbProvisionService {

    @Autowired
    private MbProvisionDao mbProvisionDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public MbProvisionResDto findById(Long id) {
        Optional<MbProvision> opt = mbProvisionDao.findById(id);
        return opt.map(provision -> ModelMapperUtils.map(provision, MbProvisionResDto.class)).orElse(null);
    }

    private Object[] createProvisionRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
                                                SearchMbProvisionModel searchModel) {
        final Root<MbProvision> rootPersist = query.from(MbProvision.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();

        if (searchModel.getProvisionName() != null && !StringUtils.isEmpty(searchModel.getProvisionName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("provisionName")),
                    "%" + searchModel.getProvisionName().toUpperCase() + "%")));
        }
        
        if (searchModel.getProvisionCode() != null && !StringUtils.isEmpty(searchModel.getProvisionCode().trim())) {
            predicates.add(cb.and(cb.like(rootPersist.<String>get("provisionCode"),"%" + searchModel.getProvisionCode() + "%")));
        }

        if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
            predicates.add(cb.and(cb.like(rootPersist.<String>get("status"),"%" + searchModel.getStatus() + "%")));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public Page<MbProvisionResDto> search(SearchMbProvisionModel searchModel, Pageable pageable) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MbProvision> query = cb.createQuery(MbProvision.class);
        Object[] queryObjs = this.createProvisionRootPersist(cb, query, searchModel);
        Root<MbProvision> root = (Root<MbProvision>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("provisionName")));

        TypedQuery<MbProvision> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<MbProvision> objects = typedQuery.getResultList();
        List<MbProvisionResDto> mbProvisionResDtos = ModelMapperUtils.mapAll(objects, MbProvisionResDto.class);

        CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(MbProvision.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(mbProvisionResDtos, pageable, total);
    }

    @Override
    @Transactional
    public MbProvisionResDto update(UpdateMbProvisionRequest request) {
        Optional<MbProvision> opt = mbProvisionDao.findById(request.getId());
        if (opt.isPresent()) {
            MbProvision save = mbProvisionDao.save(ModelMapperUtils.map(request, MbProvision.class));
            return ModelMapperUtils.map(save, MbProvisionResDto.class);
        }
        return null;
    }

    @Override
    public MbProvisionResDto create(CreateMbProvisionRequest request) {
    	MbProvision mbProvisionCheck = mbProvisionDao.findByProvisionCode(request.getProvisionCode());
		 if (mbProvisionCheck != null) {
				return null;
			}
 
        MbProvision mbProvision = ModelMapperUtils.map(request, MbProvision.class);
        mbProvision.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        return ModelMapperUtils.map(mbProvisionDao.save(mbProvision), MbProvisionResDto.class);
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        Optional<MbProvision> opt = mbProvisionDao.findById(id);
        if (opt.isPresent()) {
//            MbProvision mbProvision = opt.get();
//            mbProvision.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//            mbProvisionDao.save(mbProvision);
            mbProvisionDao.deleteById(id);
            return true;
        }
        return false;
    }

	

}
