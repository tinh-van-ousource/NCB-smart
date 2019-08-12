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
import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbFeedbackService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "ncb-feedback")
@Api(tags = "Ncb Feedback")
public class NcbFeedbackController {
	@Autowired
	private NcbFeedbackService ncbFeedbackService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbFeedbackDto>> searchNcbQA(@ModelAttribute SearchNcbFeedbackModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbFeedbackDto> dts = ncbFeedbackService.searchNcbFeedback(searchModel, pageable);
		return new ResponeData<Page<NcbFeedbackDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbFeedbackDto> detail(@RequestParam Long id) {
		NcbFeedbackDto result = ncbFeedbackService.findById(id);
		return new ResponeData<NcbFeedbackDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbFeedbackDto> create(@RequestBody CreateNcbFeedbackRequest request) {
		NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.create(request);
		if (ncbFeedbackDto == null) {
			return new ResponeData<NcbFeedbackDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
		}
		return new ResponeData<NcbFeedbackDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbFeedbackDto);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbFeedbackDto> update(@RequestBody UpdateNcbFeedbackRequest request) {
		NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.update(request);
		if (ncbFeedbackDto == null) {
			return new ResponeData<NcbFeedbackDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
		}
		return new ResponeData<NcbFeedbackDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				ncbFeedbackDto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<String> delete(@RequestParam Long id) {
		return new ResponeData<String>(ncbFeedbackService.delete(id), AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
	}
}
