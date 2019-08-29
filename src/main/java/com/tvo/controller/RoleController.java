/**
 *
 */
package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dto.RoleResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Role Controller")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/get-all")
    public ResponeData<List<RoleResDto>> getAllRole() {
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.findAllRole());
    }

    @PatchMapping(value = "/update")
    public ResponeData<RoleResDto> updateRole(@RequestBody RoleUpdateReqDto roleReqDto) {
        RoleResDto role = roleService.updateRole(roleReqDto);
        if (role != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @PostMapping(value = "/create")
    public ResponeData<RoleResDto> createRole(@RequestBody RoleCreateReqDto roleReqDto) {
        RoleResDto role = roleService.createRole(roleReqDto);
        if (role != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, role);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }
}
