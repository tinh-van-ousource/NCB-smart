package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String ccy;

    private String prdCode;

    private String feeType;

    private String createdUser;
}
