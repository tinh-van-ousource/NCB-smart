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

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchCity;
import com.tvo.controllerDto.searchModel;
import com.tvo.dao.CityDao;
import com.tvo.dto.CityDto;
import com.tvo.dto.UserDto;
import com.tvo.model.City;
import com.tvo.model.User;
import com.tvo.request.CreateCityRequest;

/**
 * @author Ace
 *
 */
@Service
public class CityServiceImpl implements CityService{
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	CityDao cityDao;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<CityDto> findAll() {
		List<CityDto> ctdt = ModelMapperUtils.mapAll(cityDao.findAll(), CityDto.class);
		return ctdt;
	}
	public Object[] createCityRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchCity resource) {
		final Root<City> rootPersist = query.from(City.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getCityCode() != null	
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCityCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("cityCode"), resource.getCityCode())));
		}
		if (resource.getCityName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCityName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("fullName"), resource.getCityName())));
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
	public CityDto createCity(CreateCityRequest request) {
		City city = cityDao.findByCityName(request.getCityName());
		if (city != null) {
			return null;
		}
		
		city = ModelMapperUtils.map(request, City.class);
		city.setCityName(request.getCityName());
		city.setCityCode(request.getCityCode());
		city.setStatus(request.getStatus());
		City save = cityDao.save(city);
		return ModelMapperUtils.map(save, CityDto.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<CityDto> searchCity(searchCity searchCity, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<City> query = cb.createQuery(City.class);
		Object[] queryObjs = this.createCityRootPersist(cb, query, searchCity);
		query.select((Root<City>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<City> typedQuery = this.entityManager.createQuery(query);
		
		typedQuery.setFirstResult((int)pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());
	    final List<City> objects = typedQuery.getResultList();
		List<CityDto> CityDtos = ModelMapperUtils.mapAll(objects, CityDto.class);

		
		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
	    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
	    countQuery.select(cbTotal.count(countQuery.from(City.class)));
	    countQuery.where((Predicate[]) queryObjs[1]);
	    Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(CityDtos, pageable, total);
	}
	
	
	

}
