/**
 * 
 */
package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.searchBranch;
import com.tvo.dao.BranchDao;
import com.tvo.dto.BranchDto;
import com.tvo.model.Branch;
import com.tvo.request.CreateBranchRequest;
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

/**
 * @author Ace
 *
 */
@Service
public class BracnhSeviceImpl implements BracnhService {
	@Autowired
	BranchDao branchDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private EntityManager entityManager;
	@Override
	public List<BranchDto> findAll() {
		List<Branch> branchs = branchDao.findAll();
		return ModelMapperUtils.mapAll(branchs, BranchDto.class);
	}

	@Override
	public BranchDto createBranch(CreateBranchRequest request) {
		Branch branch = branchDao.findByBranchName(request.getBranchName());
		if (branch != null) {
			return null;
		}
		
		branch = ModelMapperUtils.map(request, Branch.class);
		branch.setBranchId(request.getBranchId());
		branch.setBranchName(request.getBranchName());
		branch.setBranchCode(request.getBranchCode());
		Branch save = branchDao.save(branch);
		return ModelMapperUtils.map(save, BranchDto.class);
	}
	
	@Override
	public Page<BranchDto> searchBranch(searchBranch searchBranch, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Branch> query = cb.createQuery(Branch.class);
		Object[] queryObjs = this.createCityRootPersist(cb, query, searchBranch);
		query.select((Root<Branch>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<Branch> typedQuery = this.entityManager.createQuery(query);
		
		typedQuery.setFirstResult((int)pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());
	    final List<Branch> objects = typedQuery.getResultList();
		List<BranchDto> BranchDtos = ModelMapperUtils.mapAll(objects, BranchDto.class);

		
		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
	    final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//	    countQuery.select(cbTotal.count((Root<User>) queryObjs[0]));
	    countQuery.select(cbTotal.count(countQuery.from(Branch.class)));
	    countQuery.where((Predicate[]) queryObjs[1]);
	    Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(BranchDtos, pageable, total);
	}
	
	
	
	
	public Object[] createCityRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, searchBranch resource) {
		final Root<Branch> rootPersist = query.from(Branch.class);
		final List<Predicate> predicates = new ArrayList<Predicate>(6);
		if (resource.getBranchCode() != null	
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBranchCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchCode"), resource.getBranchCode())));
		}
		if (resource.getBranchName() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBranchName().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchName"), resource.getBranchName())));
		}

		if (resource.getCityCode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getCityCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("cityCode"), resource.getCityCode())));
		}
		if (resource.getDistrictCode() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getDistrictCode().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("districtCode"), resource.getDistrictCode())));
		}
		if (resource.getBranchAddress() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getBranchAddress().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("branchAddress"), resource.getBranchAddress())));
		}
		if (resource.getStatus() != null
				&& !org.apache.commons.lang3.StringUtils.isEmpty(resource.getStatus().trim())) {
			predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), resource.getStatus())));
		}

	

		Object[] results = new Object[2];
		results[0] = rootPersist;
		results[1] = predicates.toArray(new Predicate[predicates.size()]);
		return results;
	}

}
