package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchBankTransfer;
import com.tvo.dao.BankTransferDAO;
import com.tvo.dto.BankTransferDto;
import com.tvo.model.BankTransfer;
import com.tvo.request.CreateBankTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service
public class BankTransferServiceImpl implements BankTransferService{
	@Autowired
	BankTransferDAO bankDao;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private EntityManager entityManager;
	@Override
	public List<BankTransferDto> findAll() {
		List<BankTransferDto> ctdt = ModelMapperUtils.mapAll(bankDao.findAll(), BankTransferDto.class);
		return ctdt;
	}
	@Override
	public Page<BankTransferDto> searchBankTransfer(searchBankTransfer searchBankTransfer, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<BankTransfer> query = cb.createQuery(BankTransfer.class);
		Object[] queryObjs = this.createCityRootPersist(cb, query, searchBankTransfer);
		query.select((Root<BankTransfer>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);

		TypedQuery<BankTransfer> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int)pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());
	    final List<BankTransfer> objects = typedQuery.getResultList();
		List<BankTransferDto> BankTransferDtos = ModelMapperUtils.mapAll(objects, BankTransferDto.class);

		
		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
	    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
	    countQuery.select(cbTotal.count(countQuery.from(BankTransfer.class)));
	    countQuery.where((Predicate[]) queryObjs[1]);
	    Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(BankTransferDtos, pageable, total);
	}
	
	public Object[] createCityRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchBankTransfer resource) {
		final Root<BankTransfer> rootPersist = query.from(BankTransfer.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getBankCode() != null	
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBankCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchCode"), resource.getBankCode())));
		}
		if (resource.getBankName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBankName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("fullName"), resource.getBankName())));
		}

		if (resource.getStatus() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("transactionCode"), resource.getStatus())));
		}

	

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}
	@Override
	public BankTransferDto createBankTransfer(CreateBankTransferRequest request) {
		BankTransfer bankTransfer = bankDao.findByBankName(request.getBankName());
		if (bankTransfer != null) {
			return null;
		}
		
		bankTransfer = ModelMapperUtils.map(request, BankTransfer.class);
		bankTransfer.setBankCode(request.getBankCode());
		bankTransfer.setBankName(request.getBankName());
		bankTransfer.setShtname(request.getShtname());
		bankTransfer.setStatus(request.getStatus());
		bankTransfer.setBin(request.getBin());
		bankTransfer.setCitad_gt(request.getCitad_gt());
		bankTransfer.setCitad_tt(request.getCitad_tt());
		BankTransfer save = bankDao.save(bankTransfer);
		return ModelMapperUtils.map(save, BankTransferDto.class);
	}

}
