/**
 * 
 */
package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.searchModel;
import com.tvo.dto.UserDto;
import com.tvo.request.CreateUserRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.UserServiceImpl;

import io.swagger.annotations.Api;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "user Controller")
public class UserController {
	@Autowired
	UserServiceImpl userService;
	@GetMapping(value = "/searchUser")
	public ResponeData<Page<UserDto>> searchUser(@ModelAttribute searchModel searchModel, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<UserDto> UserDtos = userService.searchUser(searchModel, pageable);
		return new ResponeData<Page<UserDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, UserDtos) ;
	}
	
	@PostMapping(value = "/updateUser")
	public ResponeData<UserDto> updateUser(@ModelAttribute UserDto userDto) {
		return new ResponeData<UserDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, userService.update(userDto)) ;
	}
	
	@GetMapping(value = "/get-listUser")
	public ResponeData<Page<UserDto>> listUser( @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		Page<UserDto> page = userService.findAllUser(pageable);
		return new ResponeData<Page<UserDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, page) ;
	}
	
	@PostMapping(value="/createUser")
	public ResponeData<UserDto> createUser(@ModelAttribute CreateUserRequest request) {
		UserDto dto = userService.createUser(request);
		if(dto == null) {
			return new ResponeData<UserDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<UserDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
