/**
 *
 */
package com.tvo.service;

import com.tvo.controllerDto.UserChangePasswordReqDto;
import com.tvo.controllerDto.searchModel;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.UserDto;
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

    public UserDto update(UserDto userDto);

    public Page<UserDto> findAllUser(Pageable pageable);

    public UserDto createUser(CreateUserRequest request);

    public Page<UserDto> searchUser(searchModel searchModel, Pageable pageable);

    ContentResDto getUserDetail(Long id);

    Boolean deleteUser(Long id);

    Boolean changeUserPassword(UserChangePasswordReqDto userChangePasswordReqDto);
}
