package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.RoleResDto;
import com.tvo.response.ResponeData;
import com.tvo.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/search")
    public ResponeData<ContentResDto> searchRole(@Valid RoleSearchReqDto roleSearchReqDto) {
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.search(roleSearchReqDto));
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<RoleResDto> searchRole(@PathVariable("id") Long id) {
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.getRoleById(id));
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
