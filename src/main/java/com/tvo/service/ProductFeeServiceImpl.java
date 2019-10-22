package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.dao.ProductFeeDAO;
import com.tvo.dto.ProductFeeDto;
import com.tvo.model.ProductFeeMbApp;
import com.tvo.request.CreateProductFeeRequest;
import com.tvo.request.ProductFeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductFeeServiceImpl implements ProductFeeService {

    @Autowired
    private ProductFeeDAO productFeeDAO;

    @Override
    public ProductFeeDto detail(Long productFeeId) {
        Optional<ProductFeeMbApp> productFeeEntity = productFeeDAO.findById(productFeeId);
        ProductFeeDto productFeeDto = null;
        if (productFeeEntity.isPresent()) {
            productFeeDto = ModelMapperUtils.map(productFeeEntity.get(), ProductFeeDto.class);
        }
        return productFeeDto;
    }

    @Override
    public ProductFeeDto create(CreateProductFeeRequest productFeeRequest) {
        List<ProductFeeMbApp> productFeeEntities = productFeeDAO.findListByGrprdId(productFeeRequest.getGrprdId());
        if (productFeeEntities.isEmpty()) {
            ProductFeeMbApp productFeeEntity = ModelMapperUtils.map(productFeeRequest, ProductFeeMbApp.class);
            productFeeEntity.setCreatedTime(DateTimeUtil.getNow());
            ProductFeeMbApp saveProductFee = productFeeDAO.save(productFeeEntity);
            ProductFeeDto productFeeDto = ModelMapperUtils.map(saveProductFee, ProductFeeDto.class);
            return ModelMapperUtils.map(productFeeDto, ProductFeeDto.class);
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
    public Boolean delete(Long productFeeId) {
        Optional<ProductFeeMbApp> productFeeMbApp = productFeeDAO.findById(productFeeId);
        if (productFeeMbApp.isPresent()) {
            productFeeDAO.deleteById(productFeeId);
            return true;
        }
        return false;
    }
}
