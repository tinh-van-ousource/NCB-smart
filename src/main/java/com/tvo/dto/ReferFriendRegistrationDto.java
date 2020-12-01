package com.tvo.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Data
public class ReferFriendRegistrationDto {
    private Long id;

    private Long referFriendConfigId;

    private String rootUserCif;

    private String rootUserName;

    private String targetUserCif;

    private String status;

    private Date createdAt;

    private String createdBy;

    private String promotionCode;

    private String partnerCode;

}
