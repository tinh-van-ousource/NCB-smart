package com.tvo.service;

import com.tvo.controllerDto.SearchProductFee;
import com.tvo.dto.ConfigMbAppRsDto;
import com.tvo.dto.ProductFeeDto;
import com.tvo.request.CreateProductFeeRequest;
import com.tvo.request.ProductFeeRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFeeService {

	Page<ProductFeeDto> search(SearchProductFee searchProductFee, Pageable pageable);

	ProductFeeDto detail(Long productFeeId);

	List<ProductFeeDto> create(CreateProductFeeRequest request);

	ProductFeeDto update(ProductFeeRequest productFeeRequest);

	Boolean delete(Long productFeeRequest);
	
	List<ConfigMbAppRsDto> getListFeeTypeByCode(String code);
}


		

