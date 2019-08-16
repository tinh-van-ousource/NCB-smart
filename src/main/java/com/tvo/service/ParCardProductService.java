/**
 * 
 */
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
import org.springframework.util.ObjectUtils;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dao.ParCardProductDao;
import com.tvo.dto.ParCardProductDto;
import com.tvo.model.ParCardProduct;
import com.tvo.model.ParamManager;
import com.tvo.request.PardCardProductCreate;

/**
 * @author Ace
 *
 */
@Service
public class ParCardProductService {

	@Autowired
	ParCardProductDao parCardProductDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Page<ParCardProductDto> search(ParCardSearch searchModel, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<ParCardProduct> query = cb.createQuery(ParCardProduct.class);
		Object[] queryObjs = this.createRootPersist(cb, query, searchModel);
		query.select((Root<ParCardProduct>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<ParCardProduct> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<ParCardProduct> objects = typedQuery.getResultList();
		List<ParCardProductDto> objectDtos = ModelMapperUtils.mapAll(objects, ParCardProductDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(ParCardProduct.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(objectDtos, pageable, total);
	};

	private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ParCardSearch resource) {
		final Root<ParCardProduct> rootPersist = query.from(ParCardProduct.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getClasss() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getClasss().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("class_"), resource.getClasss())));
		}
		if (resource.getProduct() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProduct().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("product"), resource.getProduct())));
		}

		if (resource.getPrdcode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPrdcode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("prdcode"), resource.getPrdcode())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	public ParCardProduct findPrdcode(String prdcode) {
		ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(prdcode);
		if (parCardProduct == null) {
			return new ParCardProduct();
		}
		return parCardProduct;
	}

	@Transactional(readOnly = false)
	public ParCardProductDto create(PardCardProductCreate request) {
		ParCardProduct findByPrdcode = parCardProductDao.findByPrdcode(request.getPrdcode());
		if (!ObjectUtils.isEmpty(findByPrdcode)) {
			return null;
		}
		ParCardProduct data = ModelMapperUtils.map(request, ParCardProduct.class);
		ParCardProduct save = parCardProductDao.save(data);
		return ModelMapperUtils.map(save, ParCardProductDto.class);
	}

	public ParCardProductDto edit(PardCardProductCreate request) {
		ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(request.getPrdcode());
		if (!ObjectUtils.isEmpty(parCardProduct)) {
			ParCardProduct save = parCardProductDao.saveAndFlush(ModelMapperUtils.map(request, ParCardProduct.class));
			return ModelMapperUtils.map(save, ParCardProductDto.class);
		}
		return null;
	}

	public String delete(String prdCode) {
		if (!prdCode.isEmpty()) {
			ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(prdCode);
			parCardProduct.setStatus("DEACTIVE");
			parCardProductDao.saveAndFlush(parCardProduct);
			return AppConstant.SUCCSESSFUL_CODE;
		}
		return AppConstant.SYSTEM_ERORR_CODE;
	}
}
