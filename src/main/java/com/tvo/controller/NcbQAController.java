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
import com.tvo.controllerDto.SearchNcbQAModel;
import com.tvo.dto.NcbQADto;
import com.tvo.request.CreateNcbQARequest;
import com.tvo.request.UpdateNcbQARequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbQAService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "ncb-qa")
@Api(tags = "Ncb QA")
public class NcbQAController {

	@Autowired
	private NcbQAService ncbQAService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbQADto>> searchNcbQA(@ModelAttribute SearchNcbQAModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbQADto> dts = ncbQAService.searchNcbQA(searchModel, pageable);
		return new ResponeData<Page<NcbQADto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbQADto> detail(@RequestParam Long id) {
		if (id == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		NcbQADto result = ncbQAService.findById(id);
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbQADto> create(@RequestBody CreateNcbQARequest request) {
		NcbQADto ncbQADto = ncbQAService.create(request);
		if (ncbQADto == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbQADto);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbQADto> update(@RequestBody UpdateNcbQARequest request) {
		NcbQADto ncbQADto = ncbQAService.update(request);
		if (ncbQADto == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbQADto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = ncbQAService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
