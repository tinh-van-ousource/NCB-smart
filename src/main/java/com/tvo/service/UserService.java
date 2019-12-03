package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserSearchModel;
import com.tvo.controllerDto.UserUpdateReqDto;
import com.tvo.controllerDto.UserUpdateStatusReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserResDto;
import com.tvo.request.CreateUserRequest;

public interface UserService {
    UserResDto createUser(CreateUserRequest request);

    Page<UserResDto> searchUser(UserSearchModel searchModel, Pageable pageable);

    ContentResDto getUserDetail(String username);

    Boolean deleteUser(String username);

    ContentResDto update(UserUpdateReqDto userDto);
    Boolean resetPass(String username,String newPassword);

    boolean changeUserPassword(UserChangePasswordReqDto userChangePasswordReqDto);

    ContentResDto updateStatus(UserUpdateStatusReqDto userDto);
}