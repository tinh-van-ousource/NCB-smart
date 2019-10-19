package com.tvo.service;

import java.util.List;

import com.tvo.dto.FunctionAndProductFeeDto;
import com.tvo.request.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;

public interface FunctionService {

	//	public List<FunctionDto> findAll();
	Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable);
	CreateFunctionDto create(CreateFunctionRequest request);
	FunctionDto update(FunctionRequest request);

	FunctionDto delete(DeleteFunctionRequest deleteFunctionRequest);
	FunctionDto detail(String prd);

	List<String> getAllPrdName();

	FunctionAndProductFeeDto searchFunctionAndProductFree(Long functionId, Long productFeeId);

	FunctionAndProductFeeDto updateFunctionAndProductFee(FunctionAndProductFeeRq functionAndProductFeeRq);

	FunctionAndProductFeeDto deleteFunctionAndProductFee(FunctionAndProductFeeRq functionAndProductFeeRq);

	FunctionAndProductFeeDto createFunctionAndProductFee(CreateFunctionAndProductFeeRequest request);
}


		

