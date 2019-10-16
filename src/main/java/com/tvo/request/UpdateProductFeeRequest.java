package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductFeeRequest {

    private Long id;

    private String grprdId;

    private String prdName;

    private int feeAmount;

    private int feeMin;

    private int feeMax;

    private String prdCode;

    private String feeType;

    private String createdUser;
}
