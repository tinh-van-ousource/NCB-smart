package com.tvo.request;

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

    private Date start;

    private Date end;
}
