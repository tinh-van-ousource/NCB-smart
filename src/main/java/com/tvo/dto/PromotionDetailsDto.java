package com.tvo.dto;

import lombok.Data;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Data
public class PromotionDetailsDto {
    private Long id;

    private String promotionName;

    private String promotionCode;

    private String referPartnerCode;

    private String status;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;

    private String deletedAt;

    private String deletedBy;

}
