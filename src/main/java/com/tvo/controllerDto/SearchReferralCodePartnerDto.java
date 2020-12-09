package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tvo.common.DateTimeUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author thanglt on 11/16/2020
 * @version 1.0
 */
@Data
public class SearchReferralCodePartnerDto {
    private String partnerName;

    private String partnerCode;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;

    public void setFromDate(Date fromDate) {
        this.fromDate = DateTimeUtil.createStartTime(fromDate);
    }

    public void setToDate(Date toDate) {
        this.toDate = DateTimeUtil.createEndTime(toDate);
    }
}
