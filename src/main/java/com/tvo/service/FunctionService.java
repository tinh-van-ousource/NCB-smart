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
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable);
	public CreateFunctionDto create(CreateFunctionRequest request);
	public FunctionDto update(UpdateFunctionRequest request);

	public FunctionDto delete(DeleteFunctionRequest deleteFunctionRequest);
	public FunctionDto detail(String prd);

	List<String> getAllPrdName();

	FunctionAndProductFeeDto searchFunctionAndProductFree(String prd);

	FunctionAndProductFeeDto updatePopup(UpdateFunctionAndProductFeeRq functionAndProductFeeRq);

	FunctionAndProductFeeDto createFunction(CreateFunctionAndProductFeeRequest request);
}


		

