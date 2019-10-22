package com.tvo.service;

import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.DeleteFunctionRequest;
import com.tvo.request.FunctionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FunctionService {

	Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable);
	CreateFunctionDto create(CreateFunctionRequest request);
	FunctionDto update(FunctionRequest request);
	FunctionDto delete(DeleteFunctionRequest deleteFunctionRequest);
	FunctionDto detail(String prd);

	List<String> getAllPrdName();
}


		

