package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionDto;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.MbProvisionService;

import io.swagger.annotations.Api;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "mb-provision")
@Api(tags = "Mb Provision")
public class MbProvisionController {
	@Autowired
	private MbProvisionService mbProvisionService;

	@GetMapping(value = "search")
	public ResponeData<Page<MbProvisionDto>> searchMbProvision(@RequestBody SearchMbProvisionModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<MbProvisionDto> dts = mbProvisionService.searchMbProvision(searchModel, pageable);
		return new ResponeData<Page<MbProvisionDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<MbProvisionDto> detail(@RequestParam Long id) {
		MbProvisionDto result = mbProvisionService.findById(id);
		return new ResponeData<MbProvisionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<MbProvisionDto> create(@RequestBody CreateMbProvisionRequest request) {
		MbProvisionDto mbProvision = mbProvisionService.create(request);
		if (mbProvision == null) {
			return new ResponeData<MbProvisionDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
					null);
		}
		return new ResponeData<MbProvisionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				mbProvision);
	}

	@PutMapping(value = "update")
	public ResponeData<MbProvisionDto> update(@RequestBody UpdateMbProvisionRequest request) {
		MbProvisionDto mbProvision = mbProvisionService.update(request);
		if (mbProvision == null) {
			return new ResponeData<MbProvisionDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
					null);
		}
		return new ResponeData<MbProvisionDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				mbProvision);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = mbProvisionService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, false);
	}
}
