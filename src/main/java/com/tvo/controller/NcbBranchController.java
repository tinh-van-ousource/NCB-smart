package com.tvo.controller;

import org.apache.commons.lang3.StringUtils;
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
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dto.NcbBranchDto;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbBranchService;

import io.swagger.annotations.Api;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "ncb-branch")
@Api(tags = "Ncb Branch")
public class NcbBranchController {
	@Autowired
	private NcbBranchService ncbBranchService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbBranchDto>> searchDatUserProfile(@RequestBody SearchNcbBranchModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbBranchDto> dts = ncbBranchService.searchNcbBranch(searchModel, pageable);
		return new ResponeData<Page<NcbBranchDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbBranchDto> detail(@RequestParam String departCode) {
		if (StringUtils.isEmpty(departCode.trim())) {
			return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		NcbBranch ncbBranch = ncbBranchService.findByDepartCode(departCode);
		NcbBranchDto result = ModelMapperUtils.map(ncbBranch, NcbBranchDto.class);
		return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);

	}

	@PostMapping(value = "create")
	public ResponeData<NcbBranchDto> create(@RequestBody CreateNcbBranchRequest request) {
		NcbBranchDto ncbBranch = ncbBranchService.create(request);
		if (ncbBranch == null) {
			return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbBranch);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbBranchDto> update(@RequestBody CreateNcbBranchRequest request) {
		NcbBranchDto ncbBranch = ncbBranchService.update(request);
		if (ncbBranch == null) {
			return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbBranch);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String departCode) {
		Boolean deleteFlag = ncbBranchService.delete(departCode);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, false);
	}
}
