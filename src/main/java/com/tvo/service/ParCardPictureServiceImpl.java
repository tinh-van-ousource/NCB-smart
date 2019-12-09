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

import com.tvo.common.ModelMapperUtils;
import com.tvo.dao.ParCardPictureDAO;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ParCardPiture;
import com.tvo.request.CreateParCardPictureRequest;
import com.tvo.request.ParCardPictureSearchDto;
import com.tvo.request.UpdateParCardPictureRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParCardPictureServiceImpl implements ParCardPictureService {
	@Autowired
    ParCardPictureDAO parCardPictureDAO;

    @Autowired
    private FileService fileService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;
    
	@Override
	public Page<ParCardPictureDto> search(ParCardPictureSearchDto searchModel, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<ParCardPiture> query = cb.createQuery(ParCardPiture.class);
		Object[] queryObjs = this.createRootPersist(cb, query, searchModel);
		Root<ParCardPiture> root = (Root<ParCardPiture>) queryObjs[0];
		query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.asc(root.get("fileName")));

		TypedQuery<ParCardPiture> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<ParCardPiture> objects = typedQuery.getResultList();
		List<ParCardPictureDto> objectDtos = ModelMapperUtils.mapAll(objects, ParCardPictureDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(ParCardPiture.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(objectDtos, pageable, total);
	}
	 private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ParCardPictureSearchDto resource) {
	        final Root<ParCardPiture> rootPersist = query.from(ParCardPiture.class);
	        final List<Predicate> predicates = new ArrayList<Predicate>(3);
	        if (resource.getFileName() != null
	                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getFileName().trim())) {
	            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("fileName")), "%" + resource.getFileName().toUpperCase() + "%")));
	        }
	        if (resource.getStatus() != null
	                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
	            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("status")),"%" + resource.getStatus()+ "%")));
	        }
	        Object[] results = new Object[2];
	        results[0] = rootPersist;
	        results[1] = predicates.toArray(new Predicate[predicates.size()]);
	        return results;
	    }
	 @Transactional
	@Override
	public ParCardPictureDto create(CreateParCardPictureRequest request) {
		 ParCardPiture parCardPicture = parCardPictureDAO.findByFileName(request.getFileName());
		 if (parCardPicture != null) {
				return null;
			}

	        ParCardPiture data = ModelMapperUtils.map(request, ParCardPiture.class);
	        data.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
	        ParCardPiture save = parCardPictureDAO.save(data);

	        return ModelMapperUtils.map(save, ParCardPictureDto.class);
	}

	@Override
	public ParCardPictureDto update(UpdateParCardPictureRequest request) {
		ParCardPiture parCardPicture = parCardPictureDAO.findByFileName(request.getFileName());
		 if (parCardPicture == null) {
				return null;
			}

	        ParCardPiture data = ModelMapperUtils.map(request, ParCardPiture.class);
	        ParCardPiture save = parCardPictureDAO.save(data);

	        return ModelMapperUtils.map(save, ParCardPictureDto.class);
	}

	@Override
	public Boolean delete(String fileName) {
		ParCardPiture opt = parCardPictureDAO.findByFileName(fileName);
        if (opt != null) {
        	
//        	opt.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
        	parCardPictureDAO.delete(opt);
            return true;
        }
        return false;
   
	}
	@Override
	public ParCardPictureDto detail(String fileName) {
		ParCardPiture city = parCardPictureDAO.findByFileName(fileName);
        if (city == null) {
            return null;
        }
        return ModelMapperUtils.map(city, ParCardPictureDto.class);
	}
	@Override
	public List<String> getListLinkUrlMbApp() {
		return parCardPictureDAO.getListLinkUrl();
	}


}
