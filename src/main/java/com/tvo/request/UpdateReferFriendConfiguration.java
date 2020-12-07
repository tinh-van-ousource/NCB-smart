package com.tvo.request;

import lombok.Data;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Data
public class UpdateReferFriendConfiguration {
    private String title;
    private String instruction;
    private String objectUserType;
    private String urlPromotion;
    private String urlBanner;
    private String provider;
    private String status;
}
