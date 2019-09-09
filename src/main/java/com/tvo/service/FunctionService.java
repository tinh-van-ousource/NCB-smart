package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.UpdateFunctionRequest;

public interface FunctionService {
//	public List<FunctionDto> findAll();
	public Page<FunctionDto> searchFunction(SearchFunction searchFunction, Pageable pageable);
	public FunctionDto createFunction(CreateFunctionRequest request) ;
	public FunctionDto update(UpdateFunctionRequest request);
	
	public Boolean delete(Long id);
	
}


		

