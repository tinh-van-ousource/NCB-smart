package com.tvo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.ConfigMbApp;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.request.UpdateParamManagerRequest;
import lombok.AllArgsConstructor;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParamManagerServiceImpl implements ParamManagerService {

	private static final String HOT_LINE = "HOTLINE";

	@Autowired
	private ConfigMbAppDAO configMbAppDAO;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<ParamManagerDto> findAll() {
		List<ParamManagerDto> listResult = ModelMapperUtils.mapAll(configMbAppDAO.findAll(), ParamManagerDto.class);
		return listResult;
	}

	@Override
	public ConfigMbApp findByIdAndCode(Long id) {
		return configMbAppDAO.findByIdAndCode(id, HOT_LINE);
	}

	public Object[] createUserRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchParamManagerModel searchModel) {
		final Root<ConfigMbApp> rootPersist = query.from(ConfigMbApp.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("code")), HOT_LINE)));

		if (searchModel.getName() != null && !StringUtils.isEmpty(searchModel.getName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("name")),"%" + searchModel.getName().toUpperCase() + "%")));
		}
		if (searchModel.getValue() != null && !StringUtils.isEmpty(searchModel.getValue().trim())) {
			predicates.add(cb
					.and(cb.like(cb.upper(rootPersist.<String>get("value")),"%" + searchModel.getValue().toUpperCase() + "%")));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<ParamManagerDto> searchParamManager(SearchParamManagerModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<ConfigMbApp> query = cb.createQuery(ConfigMbApp.class);
		Object[] queryObjs = this.createUserRootPersist(cb, query, searchModel);
		Root<ConfigMbApp> root = (Root<ConfigMbApp>) queryObjs[0];
		query.select(root);
		query.where((Predicate[]) queryObjs[1]);
		query.orderBy(cb.asc(root.get("name")));

		TypedQuery<ConfigMbApp> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<ConfigMbApp> objects = typedQuery.getResultList();
		List<ParamManagerDto> paramManagerDtos = ModelMapperUtils.mapAll(objects, ParamManagerDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(ConfigMbApp.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(paramManagerDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public ConfigMbApp update(UpdateParamManagerRequest request) {
		Optional<ConfigMbApp> configMbAppDB = configMbAppDAO.findById(request.getId());
		if (configMbAppDB.isPresent()) {
			ConfigMbApp configMbApp = configMbAppDB.get();
			configMbApp.setName(request.getName());
			configMbApp.setValue(request.getValue());
			configMbApp.setDescription(request.getDescription());
			ConfigMbApp save = configMbAppDAO.save(configMbApp);
			return ModelMapperUtils.map(save, ConfigMbApp.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public ConfigMbApp create(CreateParamManagerRequest request) {
		ConfigMbApp configMbAppDB = configMbAppDAO.findByNameAndCode(request.getName(), HOT_LINE);
		if (configMbAppDB != null) {
			return null;
		}
		ConfigMbApp configMbApp = ModelMapperUtils.map(request, ConfigMbApp.class);
		configMbApp.setSort(String.valueOf(1));
		configMbApp.setCode(HOT_LINE);
		return ModelMapperUtils.map(configMbAppDAO.save(configMbApp), ConfigMbApp.class);
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(Long id) {
		Optional<ConfigMbApp> configMbApp = configMbAppDAO.findById(id);
		if (configMbApp.isPresent()) {
			configMbAppDAO.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public List<ConfigMbApp> saveAll(List<ParamManagerDto> paramManagerDtos) {
		List<ConfigMbApp> saveOrUpdate = new ArrayList<ConfigMbApp>();
		for (ParamManagerDto paramManagerDto : paramManagerDtos) {
			ConfigMbApp configMbAppDB = configMbAppDAO.findByNameAndCode(paramManagerDto.getName(), HOT_LINE);
			if (configMbAppDB != null) {
				configMbAppDB.setName(paramManagerDto.getName());
				configMbAppDB.setValue(paramManagerDto.getValue());
				configMbAppDB.setDescription(paramManagerDto.getDescription());
				saveOrUpdate.add(configMbAppDB);
			} else {
				ConfigMbApp configMbApp = ModelMapperUtils.map(paramManagerDto, ConfigMbApp.class);
				configMbApp.setSort(String.valueOf(1));
				configMbApp.setCode(HOT_LINE);
				saveOrUpdate.add(configMbApp);
			}
		}
		List<ConfigMbApp> saveDB = configMbAppDAO.saveAll(saveOrUpdate);
		return Lists.newArrayList(Sets.newHashSet(saveDB));
	}

}