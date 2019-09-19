package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
@Getter
@Setter
public class UpdateNcbBannerRequest {
    private Long id;

    private String bannerCode;

    private String bannerName;

    private String linkImg;

    private String linkUrlVn;

    private String linkUrlEn;

    private String status;

    private String actionScreen;

}
