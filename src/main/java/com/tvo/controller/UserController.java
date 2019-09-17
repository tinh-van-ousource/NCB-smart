package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserSearchModel;
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

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "user Controller")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/searchUser")
    public ResponeData<Page<UserResDto>> searchUser(UserSearchModel searchModel, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<UserResDto> resDto = userService.searchUser(searchModel, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

    @PostMapping(value = "/createUser")
    public ResponeData<UserResDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResDto dto = userService.createUser(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.ACCOUNT_IS_ALREADY_EXISTS_CODE, AppConstant.ACCOUNT_IS_ALREADY_EXISTS_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/{username}/detail")
    public ResponeData<ContentResDto> getUserDetail(@PathVariable("username") String username) {
        ContentResDto contentResDto = userService.getUserDetail(username);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
    }

    @DeleteMapping(value = "/{username}/delete")
    public ResponeData<Boolean> deleteUser(@PathVariable("username") String username) {
        Boolean result = userService.deleteUser(username);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }

    @PatchMapping(value = "/change-password")
    public ResponeData<Boolean> changeUserPassword(@RequestBody UserChangePasswordReqDto userChangePasswordReqDto) {
        boolean result = userService.changeUserPassword(userChangePasswordReqDto);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.OLD_PASSWORD_IS_INCORRECT_CODE, AppConstant.OLD_PASSWORD_IS_INCORRECT_MESSAGE, false);
    }

    @PatchMapping(value = "/update-user")
    public ResponeData<ContentResDto> updateUser(@RequestBody UserUpdateReqDto userDto) {
        ContentResDto contentResDto = userService.update(userDto);
        if (contentResDto.getContent() != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, contentResDto);
    }

    @PatchMapping(value = "/update-user-status")
    public ResponeData<ContentResDto> updateUser(@Valid @RequestBody UserUpdateStatusReqDto userDto) {
        ContentResDto contentResDto = userService.updateStatus(userDto);
        if (contentResDto.getContent() != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, contentResDto);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, contentResDto);
    }

}
