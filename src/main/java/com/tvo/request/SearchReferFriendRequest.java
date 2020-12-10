package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tvo.common.DateTimeUtil;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Data
public class SearchReferFriendRequest {
    private Long referFriendConfigId;

    private String rootUserCif;

    private String targetUserCif;

    private String status;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date start;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date end;

    public void setStart(Date startDate) {
        this.start = DateTimeUtil.createStartTime(startDate);
    }

    public void setEnd(Date endDate) {
        this.end = DateTimeUtil.createEndTime(endDate);
    }
}
