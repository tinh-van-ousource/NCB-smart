package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Hoang Long on 9/14/2020
 * @created 9/14/2020
 * @project NCB-smart
 */
@Entity
@Table(name = "NOTIFICATION_OBJECT_USER")
@Data
public class NotificationObjectUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ID_NOTIFICATION_OBJECT_USER")
    @SequenceGenerator(sequenceName = "NOTIFICATION_OBJECT_USER_SEQ",allocationSize = 1,name = "ID_NOTIFICATION_OBJECT_USER")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

}
