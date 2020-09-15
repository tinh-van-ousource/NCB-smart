package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateNotificationRequest {

    private String title;

    private String content;

    private String repeatType;

    @JsonFormat(pattern = "dd/MM/YY hh:mm:ss")
    private Date repeatValue;

    private String receiverUserId;

    private String objectUserType;

    private String type;

    private String status;
}
