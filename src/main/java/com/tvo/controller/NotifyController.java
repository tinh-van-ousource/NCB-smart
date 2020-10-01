package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.controllerDto.CreateNotifyDto;
import com.tvo.controllerDto.SearchNotify;
import com.tvo.dto.NotifyDto;
import com.tvo.request.CreateNotifyRequest;
import com.tvo.request.UpdateNotifyRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NotifyServiceImpl;
import io.swagger.annotations.Api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notify")
@Api(tags = "Notify")
public class NotifyController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
	@Autowired
	private NotifyServiceImpl notifyService;

	@GetMapping(value = "search")
	public ResponeData<Page<NotifyDto>> searchNcbQA(@ModelAttribute SearchNotify searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<NotifyDto> dts = notifyService.search(searchModel, pageable);
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Tìm kiếm Notify(in App)");
		return new ResponeData<Page<NotifyDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				dts);
	}

	@PostMapping(value = "create")
	public ResponeData<CreateNotifyDto> create(@RequestBody CreateNotifyRequest request) {
		
		CreateNotifyDto notifyDto = notifyService.create(request);
		if (notifyDto == null) {
			return new ResponeData<CreateNotifyDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Tạo mới Notify(in App)");
		return new ResponeData<CreateNotifyDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				notifyDto);
	}

	@PutMapping(value = "update")
	public ResponeData<NotifyDto> update(@RequestBody UpdateNotifyRequest request) {
		NotifyDto notifyDto = notifyService.update(request);
		if (notifyDto == null) {
			return new ResponeData<NotifyDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Cập nhật thông tin Notify(in App)");
		return new ResponeData<NotifyDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				notifyDto);
	}

	@GetMapping(value = "/detail")
	public ResponeData<NotifyDto> detail(@RequestParam String msgCode) {
		NotifyDto dto = notifyService.detail(msgCode);
		if (dto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Chi tiết Notify(in App)");
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String msgCode) {
		boolean deleteFlag = notifyService.delete(msgCode);
		if (deleteFlag == true) {
	        try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        hostname = ip.getHostName();
	        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
	        		"\n Account :"+Flag.userFlag.getUserName().toString()+
	        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
	        		" \n Địa chỉ IP đăng nhập : " + ip+
	        		" \n Hostname : " + hostname +
	        		" \n Xóa Notify(in App)");
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
