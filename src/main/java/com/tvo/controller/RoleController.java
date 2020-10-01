package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.RoleResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    @Autowired
    RoleService roleService;

    @GetMapping(value = "/search")
    public ResponeData<ContentResDto> searchRole(@Valid RoleSearchReqDto roleSearchReqDto) {
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
        		" \n Tìm kiếm Phân quyền");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.search(roleSearchReqDto));
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<RoleResDto> searchRole(@PathVariable("id") Long id) {
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
        		" \n Chi tiết Phân quyền");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.getRoleById(id));
    }

    @PatchMapping(value = "/update")
    public ResponeData<RoleResDto> updateRole(@RequestBody RoleUpdateReqDto roleReqDto) {
        RoleResDto role = roleService.updateRole(roleReqDto);
        if (role != null) {
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
            		" \n Cập nhật thông tin Phân quyền");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @PostMapping(value = "/create")
    public ResponeData<RoleResDto> createRole(@RequestBody RoleCreateReqDto roleReqDto) {
        RoleResDto role = roleService.createRole(roleReqDto);
        if (role != null) {
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
            		" \n Tạo mới Phân quyền");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }
    
    @DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String roleName) {
		boolean deleteFlag = roleService.delete(roleName);
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
	        		" \n Xóa Phân quyền");
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
