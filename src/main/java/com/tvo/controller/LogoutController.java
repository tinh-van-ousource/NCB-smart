package com.tvo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.response.ResponeData;

@RestController
@RequestMapping(value = "/logout")
public class LogoutController {
	@PostMapping(value="/logout")
	public ResponeData<CreateFunctionDto> create(@RequestBody CreateFunctionRequest request) {
		CreateFunctionDto createFunctionDto = functionService.create(request);
		if(createFunctionDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CREATE_FUNCTION_CODE, AppConstant.SYSTEM_ERROR_CREATE_FUNCTION_MESSAGE, null);
		}
        logger.info("Tạo mới Gói sản phẩm");
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, createFunctionDto);
	}
}
