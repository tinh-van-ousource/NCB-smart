package com.tvo.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Data
public class ReferralCodePartnerDto {
    private Long id;

    private String partnerName;

    private String partnerCode;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;

    private String deletedAt;

    private String deletedBy;
}
