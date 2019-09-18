package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.CreateNotifyDto;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNotifyRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NotifyServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/notify")
@Api(tags = "Notify")
public class NotifyController {

	@Autowired
	private NotifyServiceImpl notifyService;
	
	@GetMapping(value = "search")
	public ResponeData<Page<NotifyDto>> searchNcbQA(@ModelAttribute SearchNotify searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NotifyDto> dts = notifyService.search(searchModel, pageable);
		return new ResponeData<Page<NotifyDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}
	@PostMapping(value = "create")
	public ResponeData<CreateNotifyDto> create(@RequestBody CreateNotifyRequest request) {
		
		CreateNotifyDto notifyDto = notifyService.create(request);
		if (notifyDto == null) {
			return new ResponeData<CreateNotifyDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<CreateNotifyDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, notifyDto);
	}

	@PostMapping(value = "update")
	public ResponeData<NotifyDto> update(@RequestBody UpdateNotifyRequest request) {
		NotifyDto notifyDto = notifyService.update(request);
		if (notifyDto == null) {
			return new ResponeData<NotifyDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<NotifyDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, notifyDto);
	}
	@PostMapping(value = "/detail")
	    public ResponeData<NotifyDto> detail(@RequestParam String type) {
		 NotifyDto dto = notifyService.detail(type);
	        if (dto == null) {
	            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	        }
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	    }


	@PostMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String type) {
		boolean deleteFlag = notifyService.delete(type);
		if (deleteFlag == true) {
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
