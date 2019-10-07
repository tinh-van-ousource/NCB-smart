package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductFeeDto {

    private Long id;

    private String grprdId;

    private String prdName;

    private int feeAmount;

    private int feeMin;

    private int feeMax;

    private String prdCode;

    private String feeType;

    private String createdUser;

    private Date createdTime;
}
