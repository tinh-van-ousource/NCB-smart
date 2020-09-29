package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateNotificationRequest {

    private String title;

    private String content;

    private String repeatType;

    private String repeatValue;

    private String receiverUserId;

    private String objectUserType;

    private String type;

    private String status;

    private List<UserNotifications> userNotifications;
}
