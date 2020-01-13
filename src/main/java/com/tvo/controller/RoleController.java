package com.tvo.controller;

import com.tvo.common.AppConstant;
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

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/search")
    public ResponeData<ContentResDto> searchRole(@Valid RoleSearchReqDto roleSearchReqDto) {
    	logger.info("Tìm kiếm Phân quyền");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.search(roleSearchReqDto));
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<RoleResDto> searchRole(@PathVariable("id") Long id) {
    	logger.info("Chi tiết Phân quyền");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.getRoleById(id));
    }

    @PatchMapping(value = "/update")
    public ResponeData<RoleResDto> updateRole(@RequestBody RoleUpdateReqDto roleReqDto) {
        RoleResDto role = roleService.updateRole(roleReqDto);
        if (role != null) {
        	logger.info("Cập nhật thông tin Phân quyền");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @PostMapping(value = "/create")
    public ResponeData<RoleResDto> createRole(@RequestBody RoleCreateReqDto roleReqDto) {
        RoleResDto role = roleService.createRole(roleReqDto);
        if (role != null) {
        	logger.info("Tạo mới Phân quyền");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }
    
    @DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String roleName) {
		boolean deleteFlag = roleService.delete(roleName);
		if (deleteFlag == true) {
			logger.info("Xóa Phân quyền");
			return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}
}
