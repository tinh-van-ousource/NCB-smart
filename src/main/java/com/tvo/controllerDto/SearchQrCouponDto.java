package com.tvo.controllerDto;

import lombok.Data;

import java.util.Date;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Data
public class SearchQrCouponDto {
    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private String status;

    private String approveStatus;
}
