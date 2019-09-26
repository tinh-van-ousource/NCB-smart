/**
 * 
 */
package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchCity;
import com.tvo.dao.CityDao;
import com.tvo.dto.CityDto;
import com.tvo.dto.CreateCityDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.City;
import com.tvo.request.CreateCityRequest;
import com.tvo.request.DeleteCityRequest;
import com.tvo.request.UpdateCityRequest;
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
import java.util.Optional;

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
	public Object[] createCityRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchCity resource) {
		final Root<City> rootPersist = query.from(City.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		
		
		if (resource.getCityCode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCityCode().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("cityCode")), "%" +resource.getCityCode().toUpperCase() + "%")));
		}
		if (resource.getCityName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCityName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("cityName")), "%" + resource.getCityName().toUpperCase()+ "%")));
		}
		if (resource.getStatus() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("status")), "%" + resource.getStatus().toUpperCase()+ "%")));
		}

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}
	@Override
	public CreateCityDto createCity(CreateCityRequest request) {
		City city = cityDao.findByCityCode(request.getCityCode());
		if (city != null) {
			return null;
		}
		
		city = ModelMapperUtils.map(request, City.class);
		city.setCreatedDate(DateTimeUtil.getNow());
		City save = cityDao.save(city);
		return ModelMapperUtils.map(save, CreateCityDto.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<CityDto> searchCity(SearchCity searchCity, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<City> query = cb.createQuery(City.class);
		Object[] queryObjs = this.createCityRootPersist(cb, query, searchCity);
		Root<City> root = (Root<City>) queryObjs[0];
        query.select(root);
		query.where((Predicate[]) queryObjs[1]);

		query.orderBy(cb.desc(root.get("cityId")));
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
	@Override
	public CityDto update(UpdateCityRequest request) {
		Optional<City> opt = cityDao.findById(request.getCityId());
		if (opt.isPresent()) {
			City city = ModelMapperUtils.map(request,City.class);
			
			City save = cityDao.save(city);

			return ModelMapperUtils.map(save, CityDto.class);
		}
		return null;	
	}
	@Override
	public CityDto delete(DeleteCityRequest deleterequest) {
		City city = cityDao.findByCityCode(deleterequest.getCityCode());
		if (city == null) {
			return null;
		}
		city = ModelMapperUtils.map(deleterequest, City.class);
		city.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
		City save = cityDao.save(city);
		return ModelMapperUtils.map(save, CityDto.class);
	}
	@Override
	public CityDto detail(Long cityId) {
		City city = cityDao.findByCityId(cityId);
	        if (city == null) {
	            return null;
	        }
	        return ModelMapperUtils.map(city, CityDto.class);
	}
	
	
	

}
