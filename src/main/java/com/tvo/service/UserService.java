package com.tvo.service;

import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserSearchModel;
import com.tvo.controllerDto.UserUpdateReqDto;
import com.tvo.controllerDto.UserUpdateStatusReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserResDto;
import com.tvo.request.CreateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResDto createUser(CreateUserRequest request);

    Page<UserResDto> searchUser(UserSearchModel searchModel, Pageable pageable);

    ContentResDto getUserDetail(String username);

    Boolean deleteUser(Long userId);

    ContentResDto update(UserUpdateReqDto userDto);

    boolean changeUserPassword(UserChangePasswordReqDto userChangePasswordReqDto);

    ContentResDto updateStatus(UserUpdateStatusReqDto userDto);
}
