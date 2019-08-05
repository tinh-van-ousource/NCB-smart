/**
 * 
 */
package com.tvo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.dto.RoleDto;
import com.tvo.model.Role;
import com.tvo.response.ResponeData;
import com.tvo.service.RoleService;

import io.swagger.annotations.Api;

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
	
	@GetMapping(value = "/get-roles")
	public ResponeData<List<RoleDto>> getroDtos(){
		return new ResponeData<List<RoleDto>>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE, roleService.findAllRole()) ;
	}
	
	@PostMapping(value = "/createRole")
	public ResponeData<RoleDto> createRole(@ModelAttribute RoleDto roleDto){
		Role role = roleService.save(roleDto);
		return new ResponeData<RoleDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.map(role, RoleDto.class));
	}
}
