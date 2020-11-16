package com.tvo.request;

import lombok.Data;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Data
public class CreateOrUpdateReferralCodePartnerRequest {
    private String partnerName;

    private String partnerCode;
}
