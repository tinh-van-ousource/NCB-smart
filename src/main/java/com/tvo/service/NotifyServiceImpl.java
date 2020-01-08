package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.CreateNotifyDto;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dao.NotifyDAO;
import com.tvo.dto.NotifyDto;
import com.tvo.model.Notify;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNotifyRequest;
import lombok.AllArgsConstructor;
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
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService{
	
	@Autowired
	private NotifyDAO notifyDao;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<NotifyDto> search(SearchNotify searchNotify, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Notify> query = cb.createQuery(Notify.class);
		Object[] queryObjs = createNcbQARootPersist(cb, query, searchNotify);
		query.select((Root<Notify>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);	
		TypedQuery<Notify> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<Notify> objects = typedQuery.getResultList();
		List<NotifyDto> notifyDtos = ModelMapperUtils.mapAll(objects, NotifyDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(Notify.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(notifyDtos, pageable, total);
	}
	
	@SuppressWarnings("unused")
	private Object[] createNcbQARootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchNotify searchNotify) {
		final Root<Notify> rootPersist = query.from(Notify.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchNotify.getProvider() != null && !StringUtils.isEmpty(searchNotify.getProvider().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("provider")), "%" + searchNotify.getProvider().toUpperCase() + "%")));
		}
		if (searchNotify.getMsgCode() != null && !StringUtils.isEmpty(searchNotify.getMsgCode().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("msgCode")),
					"%" +searchNotify.getMsgCode().toUpperCase()+ "%")));
		}
		if (searchNotify.getError() != null && !StringUtils.isEmpty(searchNotify.getError().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("error")),
					"%" +searchNotify.getError().toUpperCase()+ "%")));
		}
		if (searchNotify.getType() != null && !StringUtils.isEmpty(searchNotify.getType().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("type")),
					"%" +searchNotify.getType().toUpperCase()+ "%")));
		}
		
		if (searchNotify.getMes_Vn() != null && !StringUtils.isEmpty(searchNotify.getMes_Vn().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("m")),
					"%" +searchNotify.getMes_Vn().toUpperCase()+ "%")));
		}
		
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public CreateNotifyDto create(CreateNotifyRequest request) {
		Notify notify = notifyDao.findByMsgCode(request.getType());
		if (notify != null) {
			return null;
		}
		notify = ModelMapperUtils.map(request, Notify.class);
		notify.setCreate_Date(DateTimeUtil.getNow());
		Notify save = notifyDao.save(notify);
		return ModelMapperUtils.map(save, CreateNotifyDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public NotifyDto update(UpdateNotifyRequest request) {
		Notify opt = notifyDao.findByMsgCode(request.getMsgCode());
		Date createDateOld = opt.getCreate_Date();
		if (opt != null) {
			Notify notify = ModelMapperUtils.map(request,Notify.class);
			notify.setCreate_Date(createDateOld);
			Notify save = notifyDao.save(notify);

			return ModelMapperUtils.map(save, NotifyDto.class);
		}
		return null;	
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(String msgCode) {
		Notify notify = notifyDao.findByMsgCode(msgCode);
		if (msgCode != null) {
			notifyDao.delete(notify);
			return true;
		}
		return false;

	}
	@Override
	public NotifyDto detail(String msgCode) {
        Notify notity = notifyDao.findByMsgCode(msgCode);
        if (notity == null) {
            return null;
        }
        return ModelMapperUtils.map(notity, NotifyDto.class);
	}

}
