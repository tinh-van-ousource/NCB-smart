package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Entity
@Table(name = "REFER_FRIEND_REGISTRATION")
@Data
public class ReferFriendRegistrationEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_REFER_FRIEND_REGISTRATION")
    @SequenceGenerator(sequenceName = "REFER_FRIEND_REGISTRATION_SQ", allocationSize = 1, name = "ID_REFER_FRIEND_REGISTRATION")
    @Column(name = "ID")
    private Long id;

    @Column(name = "REFER_FRIEND_CONFIG_ID")
    private Long referFriendConfigId;

    @Column(name = "ROOT_USER_CIF")
    private String rootUserCif;

    @Column(name = "TARGET_USER_CIF")
    private String targetUserCif;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "PROMOTIONCODE")
    private String promotionCode;

    @Column(name = "PARTNERCODE")
    private String partnerCode;
}
