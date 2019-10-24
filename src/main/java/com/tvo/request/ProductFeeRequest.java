package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeeRequest {

    private Long id;

    private String grprdId;

    private String prdName;

    private int feeAmount;

    private int feeMin;

    private int feeMax;

    private String ccy;

    private String prdCode;

    private String feeType;

    private String codeFee;

    private String taxPercent;

    private String createdUser;
}
