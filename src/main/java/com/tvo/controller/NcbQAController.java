package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchNcbQAModel;
import com.tvo.dto.NcbQADto;
import com.tvo.request.CreateNcbQARequest;
import com.tvo.request.UpdateNcbQARequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbQAService;
import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "ncb-qa")
@Api(tags = "Ncb QA")
public class NcbQAController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NcbQAService ncbQAService;

	@GetMapping(value = "search")
	public ResponeData<Page<NcbQADto>> searchNcbQA(@ModelAttribute SearchNcbQAModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NcbQADto> dts = ncbQAService.searchNcbQA(searchModel, pageable);
		logger.info("Tìm kiếm Thông tin Q&As");
		return new ResponeData<Page<NcbQADto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<NcbQADto> detail(@RequestParam Long id) {
		if (id == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		NcbQADto result = ncbQAService.findById(id);
		logger.info("Chi tiết Thông tin Q&As");
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
	}

	@PostMapping(value = "create")
	public ResponeData<NcbQADto> create(@RequestBody CreateNcbQARequest request) {
		NcbQADto ncbQADto = ncbQAService.create(request);
		if (ncbQADto == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		logger.info("Tạo mới Thông tin Q&As");
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbQADto);
	}

	@PutMapping(value = "update")
	public ResponeData<NcbQADto> update(@RequestBody UpdateNcbQARequest request) {
		NcbQADto ncbQADto = ncbQAService.update(request);
		if (ncbQADto == null) {
			return new ResponeData<NcbQADto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		logger.info("Cập nhật thông tin Thông tin Q&As");
		return new ResponeData<NcbQADto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbQADto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = ncbQAService.delete(id);
		if (deleteFlag == true) {
			logger.info("Xóa Thông tin Q&As");
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
