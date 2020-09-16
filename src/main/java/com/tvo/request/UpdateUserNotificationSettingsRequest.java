package com.tvo.request;

import lombok.Data;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@Data
public class UpdateUserNotificationSettingsRequest {

    private String userName;

    private Long deviceId;

    private String pushNotiSetting;

    private String smsNotiSetting;

}
