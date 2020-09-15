package com.tvo.dto;

import com.tvo.request.UserNotifications;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class NotificationsDto {

    private Long id;

    private String title;

    private String content;

    private String repeatType;

    private String repeatValue;

    private String receiverUserId;

    private String objectUserType;

    private String type;

    private String status;

    private String deletedAt;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private List<UserNotifications> userNotifications;
}
