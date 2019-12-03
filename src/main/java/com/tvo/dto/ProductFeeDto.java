package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductFeeDto {

    private Long id;

    private String grprdId;

    private String prdName;

    private String feeAmount;

    private String feeMin;

    private String feeMax;

    private String prdCode;

    private String feeType;

    private String ccy;

    private String codeFee;

    private Integer taxPercent;

    private String createdUser;
    @JsonFormat(pattern = "yyyy/M/d")
    private Date createdTime;

}
