package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchUserNotificationSettingsDto;
import com.tvo.dto.UserNotificationSettingsDto;
import com.tvo.request.UpdateUserNotificationSettingsRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.UserNotificationSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@RestController
@RequestMapping("/user-notification-settings")
public class UserNotificationSettingsController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserNotificationSettingsService userNotificationSettingsService;

    @GetMapping("")
    public ResponeData<Page<UserNotificationSettingsDto>> search(@ModelAttribute SearchUserNotificationSettingsDto searchUserNotificationSettingsDto,
                                                                 @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
        try {
           return userNotificationSettingsService.search(searchUserNotificationSettingsDto,pageable);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @PutMapping("/{id}")
    public ResponeData<UserNotificationSettingsDto> update(@PathVariable Long id,@RequestBody UpdateUserNotificationSettingsRequest updateUserNotificationSettingsRequest){
        try {
            return userNotificationSettingsService.update(id,updateUserNotificationSettingsRequest);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
    @GetMapping("/{id}")
    public ResponeData<UserNotificationSettingsDto> details(@PathVariable Long id){
        try {
            return userNotificationSettingsService.details(id);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
}
