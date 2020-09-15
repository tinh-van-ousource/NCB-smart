package com.tvo.service;

import com.tvo.controllerDto.SearchUserNotificationSettingsDto;
import com.tvo.dto.UserNotificationSettingsDto;
import com.tvo.request.UpdateUserNotificationSettingsRequest;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
public interface UserNotificationSettingsService {

    /**
     * Search User Notification Settings By Name
     *
     * @param searchUserNotificationSettingsDto searchUserNotificationSettingsDto
     * @param pageable pageable
     * @return Page<UserNotificationSettingsDto>
     * @throws Exception
     */
    ResponeData<Page<UserNotificationSettingsDto>> search(SearchUserNotificationSettingsDto searchUserNotificationSettingsDto, Pageable pageable)throws Exception;

    /**
     * Update User Notification Settings
     *
     * @param id id
     * @param updateUserNotificationSettingsRequest updateUserNotificationSettingsRequest
     * @return UserNotificationSettingsDto
     * @throws Exception
     */
    ResponeData<UserNotificationSettingsDto> update(Long id, UpdateUserNotificationSettingsRequest updateUserNotificationSettingsRequest)throws Exception;

    /**
     * Find User Notification Setting By Id
     *
     * @param id id
     * @return UserNotificationSettingsDto
     * @throws Exception
     */
    ResponeData<UserNotificationSettingsDto> details(Long id)throws Exception;

}
