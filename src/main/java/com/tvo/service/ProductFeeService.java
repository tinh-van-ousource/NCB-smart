package com.tvo.service;

import com.tvo.controllerDto.SearchProductFee;
import com.tvo.dto.ProductFeeDto;
import com.tvo.request.CreateProductFeeRequest;
import com.tvo.request.ProductFeeRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductFeeService {

	Page<ProductFeeDto> search(SearchProductFee searchProductFee, Pageable pageable);

	ProductFeeDto detail(Long productFeeId);

	ProductFeeDto create(CreateProductFeeRequest request);

	ProductFeeDto update(ProductFeeRequest productFeeRequest);

	Boolean delete(Long productFeeRequest);
}


		

