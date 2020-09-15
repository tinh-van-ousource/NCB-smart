package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tvo.common.DateTimeUtil;
import lombok.Data;

import java.util.Date;

@Data
public class SearchNotificationDto {

    private String title;

    private String content;

    private String search;

    private String repeatType;

    private String status;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;

    public void setFromDate(Date fromDate) {
        this.fromDate = DateTimeUtil.createStartTime(fromDate);
    }

    public void setToDate(Date toDate) {
        this.toDate = DateTimeUtil.createStartTime(toDate);
    }
}
