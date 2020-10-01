package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchNcbFeedbackModel;
import com.tvo.dto.NcbFeedbackDto;
import com.tvo.request.CreateNcbFeedbackRequest;
import com.tvo.request.UpdateNcbFeedbackRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbFeedbackService;
import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "ncb-feedback")
@Api(tags = "Ncb Feedback")
public class NcbFeedbackController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    @Autowired
    private NcbFeedbackService ncbFeedbackService;

    @GetMapping(value = "search")
    public ResponeData<Page<NcbFeedbackDto>> searchNcbQA(@ModelAttribute SearchNcbFeedbackModel searchModel,
                                                         @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<NcbFeedbackDto> dts = ncbFeedbackService.searchNcbFeedback(searchModel, pageable);
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
        		" \n Tìm kiếm Góp ý/Lỗi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @GetMapping(value = "detail")
    public ResponeData<NcbFeedbackDto> detail(@RequestParam Long id) {
        if (id == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        NcbFeedbackDto result = ncbFeedbackService.findById(id);
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
        		" \n Chi tiết Góp ý/Lỗi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
    }

    @PostMapping(value = "create")
    public ResponeData<NcbFeedbackDto> create(@Valid @RequestBody CreateNcbFeedbackRequest request) {
        NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.create(request);
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
        		" \n Tạo mới Góp ý/Lỗi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbFeedbackDto);
    }

    @PutMapping(value = "update")
    public ResponeData<NcbFeedbackDto> update(@Valid @RequestBody UpdateNcbFeedbackRequest request) {
        NcbFeedbackDto ncbFeedbackDto = ncbFeedbackService.update(request);
        if (ncbFeedbackDto == null) {
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
        		" \n Cập nhật thông tin Góp ý/Lỗi");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, ncbFeedbackDto);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam Long id) {
        boolean deleteFlag = ncbFeedbackService.delete(id);

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
        		" \n Xóa Góp ý/Lỗi");

        if (deleteFlag == true) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
