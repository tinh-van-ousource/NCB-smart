package com.tvo.service;

import java.util.List;

import com.tvo.dto.FunctionAndProductFeeDto;
import com.tvo.request.UpdateFunctionAndProductFeeRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.DeleteFunctionRequest;
import com.tvo.request.UpdateFunctionRequest;

public interface FunctionService {
//	public List<FunctionDto> findAll();
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable);
	public CreateFunctionDto create(CreateFunctionRequest request) ;
	public FunctionDto update(UpdateFunctionRequest request);

	public FunctionDto delete(DeleteFunctionRequest deleteFunctionRequest);
	public FunctionDto detail(String prd);

	List<String> getAllPrdName();

	FunctionAndProductFeeDto searchFunctionAndProductFree(SearchFunction searchFunction);

	FunctionAndProductFeeDto updatePopup(UpdateFunctionAndProductFeeRq functionAndProductFeeRq);
}


		

