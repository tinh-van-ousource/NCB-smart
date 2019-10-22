package com.tvo.service;

import com.tvo.dto.ProductFeeDto;
import com.tvo.request.CreateProductFeeRequest;
import com.tvo.request.ProductFeeRequest;

public interface ProductFeeService {

	ProductFeeDto detail(Long productFeeId);

	ProductFeeDto create(CreateProductFeeRequest request);

	ProductFeeDto update(ProductFeeRequest productFeeRequest);

	Boolean delete(Long productFeeRequest);
}


		

