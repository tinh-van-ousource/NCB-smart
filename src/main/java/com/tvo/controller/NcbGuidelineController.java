package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchNcbGuidelineModel;
import com.tvo.dto.NcbGuidelineDto;
import com.tvo.request.CreateNcbGuidelineRequest;
import com.tvo.request.UpdateNcbGuidelineRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbGuidelineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "ncb-guideline")
@Api(tags = "Ncb Guideline")
public class NcbGuidelineController {
	@Autowired
	private NcbGuidelineService ncbGuidelineService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbGuidelineDto>> searchNcbGuideline(@ModelAttribute SearchNcbGuidelineModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbGuidelineDto> dts = ncbGuidelineService.searchNcbGuideline(searchModel, pageable);
		return new ResponeData<Page<NcbGuidelineDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbGuidelineDto> detail(@RequestParam Long id) {
		if (id == null) {
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		NcbGuidelineDto result = ncbGuidelineService.findById(id);
		return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbGuidelineDto> create(@RequestBody CreateNcbGuidelineRequest request) {
		NcbGuidelineDto ncbGuideline = ncbGuidelineService.create(request);
		if (ncbGuideline == null) {
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbGuideline);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbGuidelineDto> update(@RequestBody UpdateNcbGuidelineRequest request) {
		NcbGuidelineDto ncbGuideline = ncbGuidelineService.update(request);
		if (ncbGuideline == null) {
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbGuideline);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = ncbGuidelineService.delete(id);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
