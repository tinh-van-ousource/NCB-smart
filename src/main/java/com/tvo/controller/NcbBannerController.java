package com.tvo.controller;

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
import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dto.NcbBannerDto;
import com.tvo.request.CreateNcbBannerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbBannerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "ncb-banner")
@Api(tags = "Ncb Banner")
public class NcbBannerController {
	@Autowired
	private NcbBannerService ncbBannerService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbBannerDto>> searchNcbBanner(@ModelAttribute SearchNcbBannerModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbBannerDto> dts = ncbBannerService.searchNcbBanner(searchModel, pageable);
		return new ResponeData<Page<NcbBannerDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbBannerDto> detail(@RequestParam Long id) {
		if (id == null) {
			return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		NcbBannerDto result = ncbBannerService.findById(id);
		return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbBannerDto> create(@RequestBody CreateNcbBannerRequest request) {
		NcbBannerDto ncbBanner = ncbBannerService.create(request);
		if (ncbBanner == null) {
			return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbBanner);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbBannerDto> update(@RequestBody UpdateNcbBannerRequest request) {
		NcbBannerDto ncbBanner = ncbBannerService.update(request);
		if (ncbBanner == null) {
			return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<NcbBannerDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbBanner);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = ncbBannerService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, false);
	}
}