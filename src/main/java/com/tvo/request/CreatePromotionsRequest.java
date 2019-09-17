package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CreatePromotionsRequest {

    private String customerType;

    private String promotionName;

    private String promotion;

    private String percentage1;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate1;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate1;

    private String percentage2;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate2;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate2;

    private String percentage3;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate3;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate3;

    private String percentage4;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate4;

    @JsonFormat(pattern = "yyyy/M/d")
    private Date toDate4;

    private String prdName;

}
