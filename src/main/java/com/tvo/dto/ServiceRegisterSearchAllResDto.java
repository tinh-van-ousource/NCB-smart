package com.tvo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterSearchAllResDto {
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

    private Long fee;

    private String salaryCode;

    private String salaryBank;

    private Long creditLimit;

    private Long monthlyIncome;

    private Long monthlySpend;

    private String autoDebit;

    private String repayMode;

    private String autoDebitBankAcct;

    private String requestDate;

    private String completedDate;

    private String status;

    private String description;

    private String userId;

    private String userRegister;

}
