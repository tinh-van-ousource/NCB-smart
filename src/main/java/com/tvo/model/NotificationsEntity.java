package com.tvo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_NOTIFICATIONS")
    @SequenceGenerator(sequenceName = "NOTIFICATIONS_SEQ", allocationSize = 1, name = "ID_NOTIFICATIONS")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "REPEAT_TYPE")
    private String repeatType;

    @Column(name = "REPEAT_VALUE")
    private String repeatValue;

    @Column(name = "RECEIVER_USER_ID")
    private String receiverUserId;

    @Column(name = "OBJECT_USER_TYPE")
    private String objectUserType;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DELETED_AT")
    private String deletedAt;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
