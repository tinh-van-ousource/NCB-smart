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
import com.tvo.controllerDto.SearchNcbGuidelineModel;
import com.tvo.dto.NcbGuidelineDto;
import com.tvo.request.CreateNcbGuidelineRequest;
import com.tvo.request.UpdateNcbGuidelineRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbGuidelineService;

import io.swagger.annotations.Api;

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
		NcbGuidelineDto result = ncbGuidelineService.findById(id);
		return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbGuidelineDto> create(@RequestBody CreateNcbGuidelineRequest request) {
		try {
			NcbGuidelineDto ncbGuideline = ncbGuidelineService.create(request);
			if (ncbGuideline == null) {
				return new ResponeData<NcbGuidelineDto>(AppConstant.ERROR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
						ncbGuideline);
			}
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
					ncbGuideline);
		} catch (Exception e) {
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE,
					null);
		}
	}

	@PutMapping(value = "update")
	public ResponeData<NcbGuidelineDto> update(@RequestBody UpdateNcbGuidelineRequest request) {
		try {
			NcbGuidelineDto ncbGuideline = ncbGuidelineService.update(request);
			if (ncbGuideline == null) {
				return new ResponeData<NcbGuidelineDto>(AppConstant.ERROR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
						ncbGuideline);
			}
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
					ncbGuideline);
		} catch (Exception e) {
			return new ResponeData<NcbGuidelineDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE,
					null);
		}
	}

	@DeleteMapping(value = "delete")
	public ResponeData<String> delete(@RequestParam Long id) {
		return new ResponeData<String>(ncbGuidelineService.delete(id), AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
	}
}
