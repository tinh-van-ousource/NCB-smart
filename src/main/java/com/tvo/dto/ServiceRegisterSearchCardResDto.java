package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterSearchCardResDto {
    private Long id;

    private String compCode;

    private String compName;

    private String customerName;

    private String cif;

    private String idCard;

    private LocalDate idCardIssueDate;

    private String phone;

    private String service;

    private String cardNo;

    private String acctNo;

    private String reissueReason;

    private String product;

    private String cardClass;

    private BigInteger fee;

    private String salaryCode;

    private String salaryBank;

    private BigInteger creditLimit;

    private BigInteger monthlyIncome;

    private BigInteger monthlySpend;

    private String autoDebit;

    private String repayMode;

    private String autoDebitBankAcct;

    private LocalDate requestDate;

    private LocalDate completedDate;

    private String status;

    private String description;

    private String userId;

    private String userRegister;

}
