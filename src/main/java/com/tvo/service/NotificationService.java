package com.tvo.service;

import com.tvo.controllerDto.SearchNotificationDto;
import com.tvo.dto.NotificationsDto;
import com.tvo.request.CreateNotificationRequest;
import com.tvo.request.UpdateNotificationRequest;
import com.tvo.response.ResponeData;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author longdev on 8/7/2020
 * @version 1.0
 */
@Service
public interface NotificationService {

    /**
     * Search Notifications by title, content, status
     *
     * @param searchNotificationDto searchNotificationDto
     * @param pageable              pageable
     * @return Page<NotificationDto>
     * @throws Exception
     */
    ResponeData<Page<NotificationsDto>> search(SearchNotificationDto searchNotificationDto, Pageable pageable) throws Exception;

    /**
     * Create Notificatons with CreateNotificationRequest.userNotification() exists in DatUserProfile
     *
     * @param createNotificationRequest createNotificationRequest
     * @return NotificationDto
     * @throws Exception
     */
    ResponeData<NotificationsDto> create(CreateNotificationRequest createNotificationRequest) throws Exception;

    /**
     * Update Notifications with CreateNotificationRequest.userNotification() exists in DatUserProfile
     *
     * @param id                        id
     * @param updateNotificationRequest updateNotificationRequest
     * @return NotificatonDto
     * @throws Exception
     */
    ResponeData<NotificationsDto> update(Long id, UpdateNotificationRequest updateNotificationRequest) throws Exception;

    /**
     * Find Notifications by id
     *
     * @param id id
     * @return NotificationDto
     * @throws Exception
     */
    ResponeData<NotificationsDto> details(Long id) throws Exception;

    /**
     * Delete Notifications
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;
}
