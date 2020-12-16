package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.controllerDto.ServiceRegisterSearchReqDto;
import com.tvo.controllerDto.ServiceRegisterUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.ServiceRegisterDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ServiceRegisterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping(value = "/service-register", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceRegisterController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    @Autowired
    private ServiceRegisterService serviceRegisterService;

    @GetMapping(value = "/search")
    public ResponeData<Page<ServiceRegisterDto>> search(@Valid ServiceRegisterSearchReqDto serviceRegisterSearchReqDto,
                                                        Pageable pageable) {
        return serviceRegisterService.search(serviceRegisterSearchReqDto, pageable);
    }

    @GetMapping(value = "/get-all-service")
    public ResponeData<List<String>> getAllService() {
        List<String> serviceList = serviceRegisterService.getAllService();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceList);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<ContentResDto> detail(@PathVariable("id") Long id) {
        ContentResDto serviceRegisterGetDetailResDto = serviceRegisterService.getServiceRegisterDetailById(id);
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
        		" \n Thao tác Chi tiết dịch vụ đã đăng ký");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterGetDetailResDto);
    }

    @PatchMapping(value = "/{service_id}/update")
    public ResponeData<ContentResDto> update(@PathVariable("service_id") Long id,
            @RequestBody ServiceRegisterUpdateReqDto serviceRegisterUpdateReqDto) {
        ContentResDto serviceRegisterGetDetailResDto = serviceRegisterService.updateServiceRegisterDetail(id, serviceRegisterUpdateReqDto);
        if (serviceRegisterGetDetailResDto != null) {
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
            		" \n Cập nhật dịch vụ đã đăng ký");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceRegisterGetDetailResDto);
        }
        
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @GetMapping(value = "depart/type-list")
    public ResponeData<List<String>> getAllActivatedDepart() {
        List<String> serviceList = serviceRegisterService.getListTypeServiceMbapp();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceList);
    }

}
