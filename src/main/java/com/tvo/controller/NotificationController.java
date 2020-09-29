package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.UserStoreException;
import com.tvo.controllerDto.SearchNotificationDto;
import com.tvo.dto.NotificationsDto;
import com.tvo.request.CreateNotificationRequest;
import com.tvo.request.UpdateNotificationRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponeData<Page<NotificationsDto>> search(@ModelAttribute SearchNotificationDto searchNotificationDto,
                                                      @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return notificationService.search(searchNotificationDto, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<NotificationsDto> create(@RequestBody CreateNotificationRequest createNotificationRequest) {
        try {
            return notificationService.create(createNotificationRequest);
        } catch (UserStoreException userStoreException) {
            logger.warn("UserName : " + "'" + userStoreException.getMessage() + "'" + " does not exits!");
            return new ResponeData<>(AppConstant.USER_NOT_EXITS_CODE, userStoreException.getMessage(), null);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<NotificationsDto> update(@PathVariable Long id,
                                                @RequestBody UpdateNotificationRequest updateNotificationRequest) {
        try {
            return notificationService.update(id, updateNotificationRequest);
        } catch (UserStoreException userStoreException) {
            logger.warn("UserName : " + "'" + userStoreException.getMessage() + "'" + " does not exits!");
            return new ResponeData<>(AppConstant.USER_NOT_EXITS_CODE, userStoreException.getMessage(), null);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<NotificationsDto> detail(@PathVariable Long id) {
        try {
            return notificationService.details(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return notificationService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
