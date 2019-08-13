package com.tvo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dao.NcbBranchDao;
import com.tvo.dto.NcbBranchDto;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;

import lombok.AllArgsConstructor;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@Service
@AllArgsConstructor
public class NcbBranchServiceImpl implements NcbBranchService {

	@Autowired
	private NcbBranchDao ncbBranchDao;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<NcbBranchDto> findAll() {
		return ModelMapperUtils.mapAll(ncbBranchDao.findAll(), NcbBranchDto.class);
	}

	@Override
	public NcbBranch findByDepartCode(String departCode) {
		NcbBranch ncbBranch = ncbBranchDao.findByDepartCode(departCode);
		if (ncbBranch == null) {
			return new NcbBranch();
		}
		return ncbBranch;
	}

	@SuppressWarnings("unused")
	private Object[] createNcbBranchRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
			SearchNcbBranchModel searchModel) {
		final Root<NcbBranch> rootPersist = query.from(NcbBranch.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchModel.getBrnCode() != null && !StringUtils.isEmpty(searchModel.getBrnCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("brnCode"), searchModel.getBrnCode())));
		}
		if (searchModel.getBranchName() != null && !StringUtils.isEmpty(searchModel.getBranchName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("branchName")),
					"%" + searchModel.getBranchName().toUpperCase() + "%")));
		}
		if (searchModel.getDepartCode() != null && !StringUtils.isEmpty(searchModel.getDepartCode().trim())) {
			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("departCode")),
					searchModel.getDepartCode().toUpperCase())));
		}
		if (searchModel.getDepartName() != null && !StringUtils.isEmpty(searchModel.getDepartName().trim())) {
			predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("departName")),
					"%" + searchModel.getDepartName().toUpperCase() + "%")));
		}
		if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
			predicates.add(cb
					.and(cb.equal(cb.upper(rootPersist.<String>get("status")), searchModel.getStatus().toUpperCase())));
		}
		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

	@Override
	public Page<NcbBranchDto> searchNcbBranch(SearchNcbBranchModel searchModel, Pageable pageable) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<NcbBranch> query = cb.createQuery(NcbBranch.class);
		Object[] queryObjs = this.createNcbBranchRootPersist(cb, query, searchModel);
		query.select((Root<NcbBranch>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<NcbBranch> typedQuery = this.entityManager.createQuery(query);

		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<NcbBranch> objects = typedQuery.getResultList();
		List<NcbBranchDto> ncbBranchDtos = ModelMapperUtils.mapAll(objects, NcbBranchDto.class);

		CriteriaBuilder cbTotal = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(NcbBranch.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(ncbBranchDtos, pageable, total);
	}

	@Override
	@Transactional(readOnly = false)
	public NcbBranchDto update(CreateNcbBranchRequest request) {
		NcbBranch ncbBranch = ncbBranchDao.findByDepartCode(request.getDepartCode());
		if (!ObjectUtils.isEmpty(ncbBranch)) {
			NcbBranch save = ncbBranchDao.save(ModelMapperUtils.map(request, NcbBranch.class));
			return ModelMapperUtils.map(save, NcbBranchDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public NcbBranchDto create(CreateNcbBranchRequest request) {
		NcbBranch findByDepartCode = ncbBranchDao.findByDepartCode(request.getDepartCode());
		if (!ObjectUtils.isEmpty(findByDepartCode)) {
			return null;
		}
		NcbBranch ncbBranch = ModelMapperUtils.map(request, NcbBranch.class);
		ncbBranch.setStatus(AppConstant.STATUS_ACTIVED);
		return ModelMapperUtils.map(ncbBranchDao.save(ncbBranch), NcbBranchDto.class);
	}

	@Override
	@Transactional(readOnly = false)
	public Boolean delete(String departCode) {
		if (!departCode.isEmpty()) {
			try {
				NcbBranch ncbBranch = ncbBranchDao.findByDepartCode(departCode);
				ncbBranch.setStatus(AppConstant.STATUS_DEACTIVED);
				ncbBranchDao.save(ncbBranch);
				return true;
			} catch (Exception e) {
				e.getStackTrace();
				return false;
			}
		}
		return false;
	}

}
