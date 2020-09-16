package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@Entity
@Table(name = "USER_NOTIFICATION_SETTINGS")
@Data
public class UserNotificationSettingsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ID_USER_NOTIFICATION_SETTINGS")
    @SequenceGenerator(sequenceName = "USER_NOTIFICATIONS_SEQ",allocationSize = 1,name = "ID_USER_NOTIFICATION_SETTINGS")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "DEVICE_ID")
    private Long deviceId;

    @Column(name = "PUSH_NOTI_SETTING")
    private String pushNotiSetting;

    @Column(name = "SMS_NOTI_SETTING")
    private String smsNotiSetting;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
