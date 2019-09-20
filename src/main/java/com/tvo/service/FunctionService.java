package com.tvo.service;

import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.UpdateFunctionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FunctionService {
//	public List<FunctionDto> findAll();
	public Page<FunctionDto> search(SearchFunction searchFunction, Pageable pageable);
	public CreateFunctionDto create(CreateFunctionRequest request) ;
	public FunctionDto update(UpdateFunctionRequest request);
	
	public Boolean delete(Long id);
	public FunctionDto detail(String prdName);

	ContentResDto getAllPrdName();
}


		

