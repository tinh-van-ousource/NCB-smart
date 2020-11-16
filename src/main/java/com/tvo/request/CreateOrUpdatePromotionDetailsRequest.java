package com.tvo.request;

import lombok.Data;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Data
public class CreateOrUpdatePromotionDetailsRequest {
    private String promotionName;

    private String promotionCode;

    private String referPartnerCode;

    private String status;
}
