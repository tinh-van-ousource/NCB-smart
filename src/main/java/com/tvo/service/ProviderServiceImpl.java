package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchProvider;
import com.tvo.dao.ProviderDAO;
import com.tvo.dto.ProviderDto;
import com.tvo.model.City;
import com.tvo.model.Provider;
import com.tvo.request.CreateProviderRequest;
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
public class ProviderServiceImpl implements ProviderService{
	@Autowired
	ProviderDAO providerDao;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private EntityManager entityManager;
	@Override
	public List<ProviderDto> findAll() {
		List<ProviderDto> ctdt = ModelMapperUtils.mapAll(providerDao.findAll(), ProviderDto.class);
		return ctdt;
	}
	@Override
	public ProviderDto createProvider(CreateProviderRequest request) {
		Provider provider = providerDao.findByProviderName(request.getProviderName());
		if (provider != null) {
			return null;
		}
		
		provider = ModelMapperUtils.map(request, Provider.class);
		provider.setProviderName(request.getProviderName());
		provider.setProviderCode(request.getProviderCode());
		provider.setProviderStatus(request.getProviderStatus());
		Provider save = providerDao.save(provider);
		return ModelMapperUtils.map(save, ProviderDto.class);
	}
	@Override
	public Page<ProviderDto> searchProvider(SearchProvider searchProvider, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Provider> query = cb.createQuery(Provider.class);
		Object[] queryObjs = this.createProviderRootPersist(cb, query, searchProvider);
		query.select((Root<Provider>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);

		TypedQuery<Provider> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int)pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());
	    final List<Provider> objects = typedQuery.getResultList();
		List<ProviderDto> ProviderDtos = ModelMapperUtils.mapAll(objects, ProviderDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
	    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
	    countQuery.select(cbTotal.count(countQuery.from(City.class)));
	    countQuery.where((Predicate[]) queryObjs[1]);
	    Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ProviderDtos, pageable, total);
	}
	public Object[] createProviderRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchProvider resource) {
		final Root<Provider> rootPersist = query.from(Provider.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getProviderCode() != null	
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProviderCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("providerCode"), resource.getProviderCode())));
		}
		if (resource.getProviderName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProviderName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("providerName"), resource.getProviderName())));
		}

		if (resource.getServiceCode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getServiceCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("serviceCode"), resource.getServiceCode())));
		}

		if (resource.getProviderPartner() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProviderPartner().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("providerPartner"), resource.getProviderPartner())));
		}

	

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}
	
	

}
