package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tvo.common.DateTimeUtil;
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

    private String discountType;

    private String serviceId;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date startDate;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date endDate;

    private String status;

    private String approveStatus;

    private String search;

    public void setStartDate(Date startDate) {
        this.startDate = DateTimeUtil.createStartTime(startDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = DateTimeUtil.createEndTime(endDate);
    }
}
