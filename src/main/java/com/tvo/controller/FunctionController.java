package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.FunctionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/function")
public class FunctionController {
	@Autowired
	FunctionServiceImpl functionService; 
	@GetMapping(value = "/searchFunction")
	public ResponeData<Page<FunctionDto>> searchFunction(@ModelAttribute SearchFunction searchFunction, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<FunctionDto> FunctionDtos = functionService.searchFunction(searchFunction, pageable);
		return new ResponeData<Page<FunctionDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, FunctionDtos) ;
	}
		@PostMapping(value="/createFunction")
		public ResponeData<FunctionDto> createBranch(@ModelAttribute CreateFunctionRequest request) {
		FunctionDto dto = functionService.createFunction(request);
		if(dto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
