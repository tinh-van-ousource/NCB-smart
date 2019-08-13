package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dto.DatUserProfileDto;
import com.tvo.response.ResponeData;
import com.tvo.service.DatUserProfileService;

import io.swagger.annotations.Api;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "user-profile")
@Api(tags = "DatUserProfile")
public class DatUserProfileController {
	@Autowired
	private DatUserProfileService datUserProfileService;

	@GetMapping(value = "searchUser")
	public ResponeData<Page<DatUserProfileDto>> searchDatUserProfile(
			@ModelAttribute SearchDatUserProfileModel searchModel, @RequestParam(value = "filter") String filter,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<DatUserProfileDto> dts = datUserProfileService.searchDatUserProfile(searchModel, filter, pageable);
		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "searchConsumer")
	public ResponeData<Page<DatUserProfileDto>> searchConsumer(@ModelAttribute SearchConsumerModel searchModel,
			@RequestParam(value = "filter") String filter,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<DatUserProfileDto> dts = datUserProfileService.searchConsumer(searchModel, filter, pageable);
		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}
}