package com.tvo.controller;

import java.util.List;

import com.tvo.dto.FunctionAndProductFeeDto;
import com.tvo.request.UpdateFunctionAndProductFeeRq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.CreateFunctionDto;
import com.tvo.controllerDto.SearchFunction;
import com.tvo.dto.CityDto;
import com.tvo.dto.FunctionDto;
import com.tvo.request.CreateFunctionRequest;
import com.tvo.request.DeleteFunctionRequest;
import com.tvo.request.UpdateFunctionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.FunctionServiceImpl;

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
	public ResponeData<CreateFunctionDto> create(@RequestBody CreateFunctionRequest request) {
		CreateFunctionDto dto = functionService.create(request);
		if(dto == null) {
			return new ResponeData<CreateFunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<CreateFunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}

	@PutMapping(value = "update")
	public ResponeData<FunctionDto> update(@RequestBody UpdateFunctionRequest request) {
		FunctionDto functionDto = functionService.update(request);
		if (functionDto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<FunctionDto> delete(@RequestBody DeleteFunctionRequest deleterequest) {
		FunctionDto functionDto = functionService.delete(deleterequest);
		if (functionDto == null) {
			return new ResponeData<FunctionDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionDto);
	}
	@GetMapping(value = "/detail")
    public ResponeData<FunctionDto> detail(@RequestParam String prd) {
		FunctionDto dto = functionService.detail(prd);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

	@GetMapping("getAllPrdName")
	public ResponeData<List<String>> getAllPrdName() {
		List<String> dto = functionService.getAllPrdName();
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}

	@GetMapping(value = "/getPopup")
	public ResponeData<FunctionAndProductFeeDto> search(@ModelAttribute SearchFunction searchFunction) {
		FunctionAndProductFeeDto functionAndProductFeeDto = functionService.searchFunctionAndProductFree(searchFunction);
		if (functionAndProductFeeDto == null) {
			return new ResponeData<FunctionAndProductFeeDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionAndProductFeeDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionAndProductFeeDto) ;
	}

	@PutMapping(value = "updatePopup")
	public ResponeData<FunctionAndProductFeeDto> updatePopup(@RequestBody UpdateFunctionAndProductFeeRq request) {
		FunctionAndProductFeeDto functionAndProductFeeDto = functionService.updatePopup(request);
		if (functionAndProductFeeDto == null) {
			return new ResponeData<FunctionAndProductFeeDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<FunctionAndProductFeeDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, functionAndProductFeeDto);
	}
}
