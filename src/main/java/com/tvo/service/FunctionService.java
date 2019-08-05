package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;

public interface FunctionService {
//	public List<FunctionDto> findAll();
	public Page<FunctionDto> searchFunction(searchFunction searchFunction, Pageable pageable);
	public FunctionDto createFunction(CreateFunctionRequest request) ;
	
}


		

