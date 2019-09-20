package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdatePromotionRequest {

    private Long id;

    private String tranType;

    private String typeId;

    private String customerType;

    private String promotion;

    private String promotionName;

    private String percentage;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;

    private String prdName;

    private String status;

    private String createdBy;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date createdDate;

}
