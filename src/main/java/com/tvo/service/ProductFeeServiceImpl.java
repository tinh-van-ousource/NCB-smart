package com.tvo.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchProductFee;
import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.dao.ProductFeeDAO;
import com.tvo.dto.ConfigMbAppRsDto;
import com.tvo.dto.ProductFeeDto;
import com.tvo.model.ProductFeeMbApp;
import com.tvo.request.CreateProductFeeRequest;
import com.tvo.request.ProductFeeRequest;

@Service
@Transactional
public class ProductFeeServiceImpl implements ProductFeeService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductFeeDAO productFeeDAO;
    
    @Autowired
    private ConfigMbAppDAO configMbAppDAO;

    @Override
    public ProductFeeDto detail(Long productFeeId) {
        Optional<ProductFeeMbApp> optProductFee = productFeeDAO.findById(productFeeId);
        if (optProductFee.isPresent()) {
            return ModelMapperUtils.map(optProductFee.get(), ProductFeeDto.class);
        }
        return null;
    }

    @Override
    public List<ProductFeeDto> create(CreateProductFeeRequest productFeeRequest) {
        List<String> listGrprdId = Arrays.asList(productFeeRequest.getGrprdId().split("\\s*,\\s*"));
        if (!listGrprdId.isEmpty()) {
            List<ProductFeeMbApp> productFeeMbApps = new ArrayList<>();
            for (String grprdId : listGrprdId) {
                ProductFeeMbApp productFeeMbAppDB = productFeeDAO
                		.findByGrprdIdAndPrdNameAndPrdCodeAndFeeType(grprdId, productFeeRequest.getPrdName(), productFeeRequest.getPrdCode(), productFeeRequest.getFeeType());
                if (productFeeMbAppDB == null) {
                	ProductFeeMbApp productFeeEntity = ModelMapperUtils.map(productFeeRequest, ProductFeeMbApp.class);
                    productFeeEntity.setGrprdId(grprdId);
                    productFeeEntity.setCreatedTime(DateTimeUtil.getNow());
                    productFeeMbApps.add(productFeeEntity);
				}
            }
            List<ProductFeeMbApp> saveProductFeeMbApps = productFeeDAO.saveAll(productFeeMbApps);
            return ModelMapperUtils.mapAll(saveProductFeeMbApps, ProductFeeDto.class);
        }
        return null;
    }

    @Override
    public ProductFeeDto update(ProductFeeRequest productFeeRequest) {
        Optional<ProductFeeMbApp> optProductFee = productFeeDAO.findById(productFeeRequest.getId());
        if (optProductFee.isPresent()) {
            ProductFeeMbApp productFeeEntity = ModelMapperUtils.map(productFeeRequest, ProductFeeMbApp.class);
            productFeeEntity.setCreatedTime(optProductFee.get().getCreatedTime());
            ProductFeeMbApp saveProductFeeEntity = productFeeDAO.save(productFeeEntity);
            ProductFeeDto productFeeDto = ModelMapperUtils.map(saveProductFeeEntity, ProductFeeDto.class);
            return ModelMapperUtils.map(productFeeDto, ProductFeeDto.class);
        }
        return null;
    }

    @Override
    public Page<ProductFeeDto> search(SearchProductFee searchProductFee, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ProductFeeMbApp> query = cb.createQuery(ProductFeeMbApp.class);
        Object[] queryObjs = this.createProductFeeMbAppRootPersist(cb, query, searchProductFee);
        Root<ProductFeeMbApp> root = (Root<ProductFeeMbApp>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);
        query.orderBy(cb.asc(root.get("prdName")));
        TypedQuery<ProductFeeMbApp> typedQuery = this.entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ProductFeeMbApp> objects = typedQuery.getResultList();
        List<ProductFeeDto> functionDtos = ModelMapperUtils.mapAll(objects, ProductFeeDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ProductFeeMbApp.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(functionDtos, pageable, total);
    }

    @Override
    public Boolean delete(Long productFeeId) {
        Optional<ProductFeeMbApp> productFeeMbApp = productFeeDAO.findById(productFeeId);
        if (productFeeMbApp.isPresent()) {
            productFeeDAO.deleteById(productFeeId);
            return true;
        }
        return false;
    }

    private Object[] createProductFeeMbAppRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchProductFee resource) {
        final Root<ProductFeeMbApp> rootPersist = query.from(ProductFeeMbApp.class);
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.and(rootPersist.get("grprdId").isNotNull()));

        if (resource.getGrprdId() != null && !StringUtils.isEmpty(resource.getGrprdId().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("grprdId")),"%" + resource.getGrprdId().toUpperCase()+ "%")));
        }
        if (resource.getPrdName() != null && !StringUtils.isEmpty(resource.getPrdName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.<String>get("prdName")),"%" + resource.getPrdName().toUpperCase()+ "%")));
        }
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

	@Override
	public List<ConfigMbAppRsDto> getListFeeTypeByCode(String code) {
		List<ConfigMbAppRsDto> configMbAppRsDtos = new ArrayList<>();
		List<Object> listObj = configMbAppDAO.findByCode(code);
		
		for (Object depart : listObj) {
            Object[] departs = (Object[]) depart;
            configMbAppRsDtos.add(
                    new ConfigMbAppRsDto(departs[0].toString(), departs[1].toString()));
        }
        return configMbAppRsDtos;
	}
}
