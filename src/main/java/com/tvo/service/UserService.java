/**
 *
 */
package com.tvo.service;

import com.tvo.controllerDto.SearchModel;
import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.UserUpdateReqDto;
import com.tvo.controllerDto.UserUpdateStatusReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserResDto;
import com.tvo.model.User;
import com.tvo.request.CreateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Ace
 *
 */
public interface UserService {
    public User getCurrenLogin();

    public Page<UserResDto> findAllUser(Pageable pageable);

    public UserResDto createUser(CreateUserRequest request);

    public Page<UserResDto> searchUser(SearchModel searchModel, Pageable pageable);

    ContentResDto getUserDetail(Long id);

    Boolean deleteUser(Long id);

    ContentResDto update(UserUpdateReqDto userDto);

    Boolean changeUserPassword(UserChangePasswordReqDto userChangePasswordReqDto);

    ContentResDto updateStatus(UserUpdateStatusReqDto userDto);
}
