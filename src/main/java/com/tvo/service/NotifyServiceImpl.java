package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dao.NotifyDAO;
import com.tvo.dto.BankTransferDto;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.NotifyDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.BankTransfer;
import com.tvo.model.Function;
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
import java.util.List;
import java.util.Optional;
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

		if (searchNotify.getMsg_Code() != null && !StringUtils.isEmpty(searchNotify.getMsg_Code().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("msg_Code")),
					searchNotify.getMsg_Code().toUpperCase())));
		}
		if (searchNotify.getMsg_Code_1() != null && !StringUtils.isEmpty(searchNotify.getMsg_Code_1().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("msg_Code_1")),
					searchNotify.getMsg_Code_1().toUpperCase())));
		}
		if (searchNotify.getProvider() != null && !StringUtils.isEmpty(searchNotify.getProvider().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("provider")),
					searchNotify.getProvider().toUpperCase())));
		}
		if (searchNotify.getType() != null && !StringUtils.isEmpty(searchNotify.getType().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("type")),
					searchNotify.getType().toUpperCase())));
		}
//		if (searchNotify.getUser_Id() != null && !StringUtils.isEmpty(searchNotify.getUser_Id().trim())) {
//			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("user_id")),
//					searchNotify.getUser_Id().toUpperCase())));
//		}
		if (searchNotify.getMes_Vn() != null && !StringUtils.isEmpty(searchNotify.getMes_Vn().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("mes_vn")),
					searchNotify.getMes_Vn().toUpperCase())));
		}
		if (searchNotify.getMes_En() != null && !StringUtils.isEmpty(searchNotify.getMes_En().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("mes_en")),
					searchNotify.getMes_En().toUpperCase())));
		}
		if (searchNotify.getError() != null && !StringUtils.isEmpty(searchNotify.getError().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("error")),
					searchNotify.getError().toUpperCase())));
		}
		
		
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public NotifyDto create(CreateNotifyRequest request) {
		Notify notify = notifyDao.findByType(request.getType());
		if (notify != null) {
			return null;
		}
		notify = ModelMapperUtils.map(request, Notify.class);
		notify.setType(request.getType());
		notify.setProvider(request.getProvider());
		notify.setMsg_Code_1(request.getMsg_Code_1());
		notify.setMsg_Code(request.getMsg_Code());
		notify.setUser_Id(request.getUser_Id());
		notify.setError(request.getError());
		notify.setCreate_Date(request.getCreate_Date());
		notify.setMes_En(request.getMes_En());
		notify.setMes_Vn(request.getMes_Vn());
		
		Notify save = notifyDao.save(notify);
		return ModelMapperUtils.map(save, NotifyDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public NotifyDto update(UpdateNotifyRequest request) {
		Optional<Notify> opt = notifyDao.findById(request.getType());
		if (opt.isPresent()) {
			Notify function = ModelMapperUtils.map(request,Notify.class);
			function.setCreate_Date(DateTimeUtil.getNow());
			
			Notify save = notifyDao.save(function);

			return ModelMapperUtils.map(save, NotifyDto.class);
		}
		return null;	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(String type) {
		Notify notify = notifyDao.findByType(type);
		if (type != null) {
			notifyDao.delete(notify);
			return true;
		}
		return false;

	}
	@Override
	public NotifyDto detail(String type) {
        Notify notity = notifyDao.findByType(type);
        if (notity == null) {
            return null;
        }
        return ModelMapperUtils.map(notity, NotifyDto.class);
	}

}
