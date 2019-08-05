package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.searchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.FunctionServiceImpl;

@RestController
@RequestMapping(value = "/function")
public class FunctionController {
	@Autowired
	FunctionServiceImpl functionService; 
	@GetMapping(value = "/searchFunction")
	public ResponeData<Page<FunctionDto>> searchFunction(@ModelAttribute searchFunction searchFunction, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<FunctionDto> FunctionDtos = functionService.searchFunction(searchFunction, pageable);
		return new ResponeData<Page<FunctionDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, FunctionDtos) ;
	}
		@PostMapping(value="/createFunction")
		public ResponeData<FunctionDto> createBranch(@ModelAttribute CreateFunctionRequest request) {
		FunctionDto dto = functionService.createFunction(request);
		if(dto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
