package com.tvo.controllerDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tvo.common.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ServiceRegisterSearchReqDto {

    private String compCode; // ma chi nhanh - phong giao dich

    private String idCard; // cmnd - ho chieu

    private String type; // phan he

    private String service; // dich vu

    private String status; // trang thai

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate; // tu ngay

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate; // den ngay

    public void setFromDate(Date fromDate) {
        this.fromDate = DateTimeUtil.createStartTime(fromDate);
    }

    public void setToDate(Date toDate) {
        this.toDate = DateTimeUtil.createEndTime(toDate);
    }

}
