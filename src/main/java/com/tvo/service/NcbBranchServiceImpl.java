package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dao.NcbBranchDao;
import com.tvo.dto.CompDroplistBranchDto;
import com.tvo.dto.NcbActiveBranchOnlyResDto;
import com.tvo.dto.NcbActiveDepartOnlyResDto;
import com.tvo.dto.NcbBranchDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;
import com.tvo.request.UpdateNcbBranchRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thanglt
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
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("brnCode")), "%" + searchModel.getBrnCode().toUpperCase() + "%")));
        }
        if (searchModel.getBranchName() != null && !StringUtils.isEmpty(searchModel.getBranchName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("branchName")),
                    "%" + searchModel.getBranchName().toUpperCase() + "%")));
        }
        if (searchModel.getDepartCode() != null && !StringUtils.isEmpty(searchModel.getDepartCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("departCode")),
                    "%" + searchModel.getDepartCode().toUpperCase() + "%")));
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
        Root<NcbBranch> root = (Root<NcbBranch>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("branchName")));

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
    public NcbBranchDto update(UpdateNcbBranchRequest request) {
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
        ncbBranch.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        return ModelMapperUtils.map(ncbBranchDao.save(ncbBranch), NcbBranchDto.class);
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean delete(String departCode) {
        if (!departCode.isEmpty()) {
            try {
                NcbBranch ncbBranch = ncbBranchDao.findByDepartCode(departCode);
                if(ncbBranch==null){
                    return false;
                }
//                ncbBranch.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
//                ncbBranchDao.save(ncbBranch);
                ncbBranchDao.delete(ncbBranch);
                return true;
            } catch (Exception e) {
                e.getStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public List<NcbActiveBranchOnlyResDto> getAllActivatedBranch() {
        List<NcbActiveBranchOnlyResDto> ncbActiveBranchOnlyResDtoList = new ArrayList<>();
        List<Object> listBranch = ncbBranchDao.retrieveAllActivatedBranch();
        for (Object branch : listBranch) {
            Object[] branches = (Object[]) branch;
            ncbActiveBranchOnlyResDtoList.add(
                    new NcbActiveBranchOnlyResDto(branches[0].toString(), branches[1].toString()));
        }

        return ncbActiveBranchOnlyResDtoList;
    }

    @Override
    public List<NcbActiveDepartOnlyResDto> getAllActivatedDepart() {
        List<NcbActiveDepartOnlyResDto> ncbActiveDepartOnlyResDtoList = new ArrayList<>();
        List<Object> listDepart = ncbBranchDao.retrieveAllActivatedDepart();
        for (Object depart : listDepart) {
            Object[] departs = (Object[]) depart;
            ncbActiveDepartOnlyResDtoList.add(
                    new NcbActiveDepartOnlyResDto(departs[0].toString(), departs[1].toString()));
        }

        return ncbActiveDepartOnlyResDtoList;
    }

	@Override
	public List<CompDroplistBranchDto> getCompDroplist() {
		List<CompDroplistBranchDto> compDroplistBranchDtoList =new ArrayList<>();
		List<Object> listComp = ncbBranchDao.getListCompCode();
        for (Object depart : listComp) {
            Object[] departs = (Object[]) depart;
            compDroplistBranchDtoList.add(
                    new CompDroplistBranchDto(departs[0].toString(), departs[1].toString()));
        }

        return compDroplistBranchDtoList;
	}

}
