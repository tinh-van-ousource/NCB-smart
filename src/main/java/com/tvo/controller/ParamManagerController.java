package com.tvo.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ParamManagerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "param-manager")
@Api(tags = "ParamManager")
public class ParamManagerController {
	@Autowired
	private ParamManagerService paramManagerService;

	@GetMapping(value = "search")
	public ResponeData<Page<ParamManagerDto>> searchDatUserProfile(@ModelAttribute SearchParamManagerModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParamManagerDto> dts = paramManagerService.searchParamManager(searchModel, pageable);
		return new ResponeData<Page<ParamManagerDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<ParamManagerDto> detail(@RequestParam String paramNo) {
		if (StringUtils.isEmpty(paramNo.trim())) {
			return new ResponeData<ParamManagerDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		ParamManager paramManager = paramManagerService.findByParamNo(paramNo);
		ParamManagerDto result = ModelMapperUtils.map(paramManager, ParamManagerDto.class);
		return new ResponeData<ParamManagerDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<ParamManager> create(@RequestBody CreateParamManagerRequest request) {
		ParamManager paramManager = paramManagerService.create(request);
		if (paramManager == null) {
			return new ResponeData<ParamManager>(AppConstant.SYSTEM_ERROR_MESSAGE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		return new ResponeData<ParamManager>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				paramManager);

	}

	@PutMapping(value = "update")
	public ResponeData<ParamManager> update(@RequestBody CreateParamManagerRequest request) {
		ParamManager paramManager = paramManagerService.update(request);
		if (paramManager == null) {
			return new ResponeData<ParamManager>(AppConstant.SYSTEM_ERROR_MESSAGE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		return new ResponeData<ParamManager>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				paramManager);

	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String paramNo) {
		boolean deleteFlag = paramManagerService.delete(paramNo);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
