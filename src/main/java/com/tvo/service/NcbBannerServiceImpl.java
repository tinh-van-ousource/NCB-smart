package com.tvo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.dao.NcbBannerDao;
import com.tvo.dto.ConfigMbAppRsDto;
import com.tvo.dto.NcbBannerDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.NcbBanner;
import com.tvo.request.CreateNcbBannerRequest;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
@Service
public class NcbBannerServiceImpl implements NcbBannerService {

	private static final String CODE_FORWARDING_SCREEN = "FUNCTION_TYPE";
	
    @Autowired
    private NcbBannerDao ncbBannerDao;
    @Autowired
	private EntityManagerFactory entityManagerFactory;
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ConfigMbAppDAO configMbAppDAO;

    public NcbBannerServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public NcbBannerDto findById(Long id) {
        Optional<NcbBanner> opt = ncbBannerDao.findById(id);
        return opt.map(banner -> ModelMapperUtils.map(banner, NcbBannerDto.class)).orElse(null);

    }

    public Object[] createNcbBannerRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query,
                                                SearchNcbBannerModel searchModel) {
        final Root<NcbBanner> rootPersist = query.from(NcbBanner.class);
        final List<Predicate> predicates = new ArrayList<Predicate>(6);

        if (searchModel.getBannerCode() != null && !StringUtils.isEmpty(searchModel.getBannerCode().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("bannerCode")),
                    "%" + searchModel.getBannerCode().toUpperCase() + "%")));
        }
        if (searchModel.getBannerName() != null && !StringUtils.isEmpty(searchModel.getBannerName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("bannerName")),
                    "%" + searchModel.getBannerName().toUpperCase() + "%")));
        }
        if (searchModel.getStatus() != null && !StringUtils.isEmpty(searchModel.getStatus().trim())) {
            predicates.add(cb.and(cb.like(rootPersist.<String>get("status"), "%" + searchModel.getStatus()+ "%")));
        }
//        if (searchModel.getStatus() != null
//				&& !org.apache.commons.lang3.StringUtils.isEmpty(searchModel.getStatus().trim())) {
//			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("status")), searchModel.getStatus().toUpperCase())));
//		}
//		if (searchModel.getBannerCode() != null
//				&& !org.apache.commons.lang3.StringUtils.isEmpty(searchModel.getBannerCode().trim())) {
//			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("prd")), searchModel.getBannerCode().toUpperCase())));
//		}
//		if (searchModel.getBannerName() != null
//				&& !org.apache.commons.lang3.StringUtils.isEmpty(searchModel.getBannerName().trim())) {
//			predicates.add(cb.and(cb.equal(cb.upper(rootPersist.<String>get("tranType")), searchModel.getBannerName().toUpperCase())));
//		}
		
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public Page<NcbBannerDto> searchNcbBanner(SearchNcbBannerModel searchModel, Pageable pageable) {
        CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<NcbBanner> query = cb.createQuery(NcbBanner.class);
        Object[] queryObjs = this.createNcbBannerRootPersist(cb, query, searchModel);
        Root<NcbBanner> root = (Root<NcbBanner>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("bannerCode")));

        TypedQuery<NcbBanner> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<NcbBanner> objects = typedQuery.getResultList();

        List<NcbBannerDto> ncbBannerDtos = ModelMapperUtils.mapAll(objects, NcbBannerDto.class);
        CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(NcbBanner.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(ncbBannerDtos, pageable, total);
    }

    @Override
    public NcbBanner create(CreateNcbBannerRequest request) {
        NcbBanner ncbBanner = new NcbBanner();
       
//        List<NcbBanner> listBanner = new ArrayList<NcbBanner>();
        
//        if (StringUtils.equals(request.getBannerCode(), "HOME_BANNER")) {
            ncbBanner = createNcbBanner(request);
//        } 
//        
//        else {
//        	listBanner = ncbBannerDao.getAllActiveBanner(request.getBannerCode());
//            if(!listBanner.isEmpty()) {
//                return null;
//            } else {    
//                ncbBanner = createNcbBanner(request);
//            }
//        }
        return ncbBanner;
    }


    @Override
    public NcbBannerDto update(UpdateNcbBannerRequest request) {
        Optional<NcbBanner> opt = ncbBannerDao.findById(request.getId());
        if (opt.isPresent()) {
            NcbBanner ncbBanner = ModelMapperUtils.map(request, NcbBanner.class);
            ncbBanner.setCreatedDate(opt.get().getCreatedDate());
            NcbBanner save = ncbBannerDao.save(ncbBanner);
            return ModelMapperUtils.map(save, NcbBannerDto.class);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        if (id != null) {
            Optional<NcbBanner> opt = ncbBannerDao.findById(id);
            if (opt.isPresent()) {
                NcbBanner ncbBanner = opt.get();
//                ncbBanner.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
                ncbBannerDao.delete(ncbBanner);
                return true;
            }
        }
        return false;
    }

    private NcbBanner createNcbBanner(CreateNcbBannerRequest request) {
        NcbBanner ncbBanner = ModelMapperUtils.map(request, NcbBanner.class);
        ncbBanner.setCreatedDate(LocalDateTime.now());
        ncbBanner.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        return ncbBannerDao.save(ncbBanner);
    }

	@Override
	public List<ConfigMbAppRsDto> getListForwardingScreen() {
		List<ConfigMbAppRsDto> configMbAppRsDtos = new ArrayList<>();
		List<Object> listObj = configMbAppDAO.findByCode(CODE_FORWARDING_SCREEN);
		
		for (Object depart : listObj) {
            Object[] departs = (Object[]) depart;
            configMbAppRsDtos.add(
                    new ConfigMbAppRsDto(departs[0].toString(), departs[1].toString()));
        }
        return configMbAppRsDtos;
	}
}
