package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateNotificationRequest {

    private String title;

    private String content;

    private String repeatType;

    @JsonFormat(pattern = "dd/MM/YY hh:mm:ss")
    private Date repeatValue;

    private String receiverUserId;

    private String objectUserType;

    private String type;

    private String status;

    private List<UserNotifications> userNotifications;

}
