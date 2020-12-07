package com.tvo.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Data
public class ReferFriendConfigurationDto {
    private Long id;

    private String title;

    private String instruction;

    private String objectUserType;

    private String urlPromotion;

    private String urlBanner;

    private String provider;

    private Date deletedAt;

    private Date createdAt;

    private String status;

}
