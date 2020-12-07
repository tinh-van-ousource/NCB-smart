package com.tvo.model;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REFER_FRIEND_CONFIGURATION")
@Data
public class ReferFriendConfigurationEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_REFER_FRIEND_CONFIGURATION")
    @SequenceGenerator(sequenceName = "REFER_FRIEND_CONFIGURATION_SQ", allocationSize = 1, name = "ID_REFER_FRIEND_CONFIGURATION")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "INSTRUCTION")
    private String instruction;

    @Column(name = "OBJECT_USER_TYPE")
    private String objectUserType;

    @Column(name = "URL_PROMOTION")
    private String urlPromotion;

    @Column(name = "URL_BANNER")
    private String urlBanner;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "DELETED_AT")
    private Date deletedAt;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "STATUS")
    private String status;

}
