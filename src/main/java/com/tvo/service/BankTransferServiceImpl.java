package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchBankTransfer;
import com.tvo.dao.BankTransferDAO;
import com.tvo.dto.BankTransferDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.BankTransfer;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.request.UpdateBankTransferRequest;
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
public class BankTransferServiceImpl implements BankTransferService {

    @Autowired
    private BankTransferDAO bankDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<BankTransferDto> search(SearchBankTransfer searchBankTransfer, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<BankTransfer> query = cb.createQuery(BankTransfer.class);
        Object[] queryObjs = this.createRootPersist(cb, query, searchBankTransfer);
        Root<BankTransfer> root = (Root<BankTransfer>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("bankName")));

        TypedQuery<BankTransfer> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        final List<BankTransfer> objects = typedQuery.getResultList();
        List<BankTransferDto> BankTransferDtos = ModelMapperUtils.mapAll(objects, BankTransferDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(BankTransfer.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(BankTransferDtos, pageable, total);
    }

    private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchBankTransfer resource) {
        final Root<BankTransfer> rootPersist = query.from(BankTransfer.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getBankCode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBankCode().trim())) {
            predicates.add(cb.and(cb.like(cb.lower(rootPersist.<String>get("bankCode")), "%" + resource.getBankCode().toLowerCase() + "%")));

        }
        if (resource.getStatus() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("status")), "%" +resource.getStatus()+ "%")));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public BankTransferDto create(CreateBankTransferRequest request) {
        BankTransfer bankTransfer = bankDao.findByBankCode(request.getBankCode());
        if (bankTransfer != null) {
            return null;
        }

        bankTransfer = ModelMapperUtils.map(request, BankTransfer.class);
        bankTransfer.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());

        BankTransfer save = bankDao.save(bankTransfer);
        return ModelMapperUtils.map(save, BankTransferDto.class);
    }

    @Override
    public BankTransferDto update(UpdateBankTransferRequest request) {
        BankTransfer bankTransfer = bankDao.findByBankCode(request.getBankCode());
        if (bankTransfer == null) {
            return null;
        }

        bankTransfer = ModelMapperUtils.map(request, BankTransfer.class);

        BankTransfer save = bankDao.save(bankTransfer);
        return ModelMapperUtils.map(save, BankTransferDto.class);
    }

    @Override
    public BankTransferDto detail(String bankCode) {
        BankTransfer bankTransfer = bankDao.findByBankCode(bankCode);
        if (bankTransfer == null) {
            return null;
        }
        return ModelMapperUtils.map(bankTransfer, BankTransferDto.class);
    }

    @Override
    public BankTransferDto deActive(String bankCode) {
        BankTransfer bankTransfer = bankDao.findByBankCode(bankCode);
        if (bankTransfer == null) {
            return null;
        }

        bankTransfer.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
        return ModelMapperUtils.map(bankDao.save(bankTransfer), BankTransferDto.class);
    }

	@Override
	public Boolean delete(String bankCode) {
		try {
			bankDao.deleteById(bankCode);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

}
