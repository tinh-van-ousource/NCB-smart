package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateFunctionRequest;
import com.tvo.request.UpdateNotifyRequest;
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
	@GetMapping(value = "/search")
	public ResponeData<Page<FunctionDto>> search(@ModelAttribute SearchFunction searchFunction, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<FunctionDto> FunctionDtos = functionService.search(searchFunction, pageable);
		return new ResponeData<Page<FunctionDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, FunctionDtos) ;
	}
	@PostMapping(value="/create")
		public ResponeData<FunctionDto> create(@ModelAttribute CreateFunctionRequest request) {
		FunctionDto dto = functionService.create(request);
		if(dto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	

	@PatchMapping(value = "update")
	public ResponeData<FunctionDto> update(@ModelAttribute UpdateFunctionRequest request) {
		FunctionDto functionDto = functionService.update(request);
		if (functionDto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = functionService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
