package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.controllerDto.SearchProductFee;
import com.tvo.dto.FunctionDto;
import com.tvo.dto.ProductFeeDto;
import com.tvo.request.*;
import com.tvo.response.ResponeData;
import com.tvo.service.FunctionService;
import com.tvo.service.ProductFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private ProductFeeService productFeeService;

	@GetMapping(value = "/search")
	public ResponeData<Page<FunctionDto>> search(@ModelAttribute SearchFunction searchFunction, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<FunctionDto> functionDtos = functionService.search(searchFunction, pageable);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDtos);
	}

	@PostMapping(value="/create")
	public ResponeData<CreateFunctionDto> create(@RequestBody CreateFunctionRequest request) {
		CreateFunctionDto createFunctionDto = functionService.create(request);
		if(createFunctionDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, createFunctionDto);
	}

	@PutMapping(value = "/update")
	public ResponeData<FunctionDto> update(@RequestBody FunctionRequest request) {
		FunctionDto functionDto = functionService.update(request);
		if (functionDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDto);
	}

	@DeleteMapping(value = "/delete")
	public ResponeData<FunctionDto> delete(@RequestParam Long functionId) {
		FunctionDto functionDto = functionService.delete(functionId);
		if (functionDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDto);
	}

	@GetMapping(value = "/detail")
	public ResponeData<FunctionDto> detail(@RequestParam Long functionId) {
		FunctionDto dto = functionService.detail(functionId);
		if (dto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}

	@PostMapping(value="/fee/create")
	public ResponeData<ProductFeeDto> createProductFee(@RequestBody CreateProductFeeRequest request) {
		ProductFeeDto productFeeDto = productFeeService.create(request);
		if(productFeeDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, productFeeDto);
	}

	@PutMapping(value = "/fee/update")
	public ResponeData<ProductFeeDto> updateFunctionFee(@RequestBody ProductFeeRequest request) {
		ProductFeeDto productFeeDto = productFeeService.update(request);
		if (productFeeDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, productFeeDto);
	}

	@DeleteMapping(value = "/fee/delete")
	public ResponeData<Boolean> deleteFunctionFee(@RequestParam Long functionFeeId) {
		Boolean isDelete = productFeeService.delete(functionFeeId);
		if (isDelete == false) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
	}

	@GetMapping(value = "/fee/detail")
	public ResponeData<ProductFeeDto> detailFunctionFee(@RequestParam Long productFeeId) {
		ProductFeeDto productFeeDto = productFeeService.detail(productFeeId);
		if (productFeeDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, productFeeDto);
	}

	@GetMapping(value = "/fee/search")
	public ResponeData<Page<ProductFeeDto>> searchFunctionFee(@ModelAttribute SearchProductFee searchProductFee, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ProductFeeDto> productFeeDtos = productFeeService.search(searchProductFee, pageable);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, productFeeDtos);
	}

	@GetMapping("getAllPrdName")
	public ResponeData<List<String>> getAllPrdName() {
		List<String> dto = functionService.getAllPrdName();
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	
}
