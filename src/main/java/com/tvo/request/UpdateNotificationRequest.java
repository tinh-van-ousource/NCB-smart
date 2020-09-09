package com.tvo.request;

import lombok.Data;

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

}
