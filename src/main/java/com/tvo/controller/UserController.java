/**
 *
 */
package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchModel;
import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserUpdateReqDto;
import com.tvo.controllerDto.UserUpdateStatusReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserResDto;
import com.tvo.request.CreateUserRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponeData<Page<UserResDto>> searchUser(@ModelAttribute SearchModel searchModel, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<UserResDto> UserDtos = userService.searchUser(searchModel, pageable);
        return new ResponeData<Page<UserResDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, UserDtos);
    }

    @GetMapping(value = "/get-listUser")
    public ResponeData<Page<UserResDto>> listUser(@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<UserResDto> page = userService.findAllUser(pageable);
        return new ResponeData<Page<UserResDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, page);
    }

    @PostMapping(value = "/createUser")
    public ResponeData<UserResDto> createUser(@RequestBody CreateUserRequest request) {
        UserResDto dto = userService.createUser(request);
        if (dto == null) {
            return new ResponeData<UserResDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<UserResDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponeData<ContentResDto> getUserDetail(@PathVariable("id") Long id) {
        ContentResDto contentResDto = userService.getUserDetail(id);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponeData<Boolean> deleteUser(@PathVariable("id") Long id) {
        Boolean result = userService.deleteUser(id);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, result);
    }

    @PatchMapping(value = "/change-password")
    public ResponeData<Boolean> changeUserPassword(@RequestBody UserChangePasswordReqDto userChangePasswordReqDto) {
        Boolean result = userService.changeUserPassword(userChangePasswordReqDto);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, result);
    }

    @PatchMapping(value = "/update-user")
    public ResponeData<ContentResDto> updateUser(@RequestBody UserUpdateReqDto userDto) {
        ContentResDto contentResDto = userService.update(userDto);
        if (contentResDto.getContent().equals(true)) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_CODE, contentResDto);
    }

    @PatchMapping(value = "/update-user-status")
    public ResponeData<ContentResDto> updateUser(@Valid @RequestBody UserUpdateStatusReqDto userDto) {
        ContentResDto contentResDto = userService.updateStatus(userDto);
        if (contentResDto.getContent().equals(true)) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_CODE, contentResDto);
    }

}
