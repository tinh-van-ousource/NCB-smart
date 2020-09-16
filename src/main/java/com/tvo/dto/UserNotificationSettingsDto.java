package com.tvo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@Data
public class UserNotificationSettingsDto {

    private Long id;

    private String userName;

    private Long deviceId;

    private String pushNotiSetting;

    private String smsNotiSetting;

    private String createdAt;

    private String updatedAt;
}
