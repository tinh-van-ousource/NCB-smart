package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterSearchCardResDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("comp_code")
    private String compCode;

    @JsonProperty("comp_name")
    private String compName;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("cif")
    private String cif;

    @JsonProperty("id_card")
    private String idCard;

    @JsonProperty("id_card_issue_date")
    private String idCardIssueDate;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("service")
    private String service;

    @JsonProperty("card_no")
    private String cardNo;

    @JsonProperty("acct_no")
    private String acctNo;

    @JsonProperty("reissue_reason")
    private String reissueReason;

    @JsonProperty("product")
    private String product;

    @JsonProperty("card_class")
    private String cardClass;

    @JsonProperty("fee")
    private BigInteger fee;

    @JsonProperty("salary_code")
    private String salaryCode;

    @JsonProperty("salary_bank")
    private String salaryBank;

    @JsonProperty("credit_limit")
    private BigInteger creditLimit;

    @JsonProperty("monthly_income")
    private BigInteger monthlyIncome;

    @JsonProperty("monthly_spend")
    private BigInteger monthlySpend;

    @JsonProperty("auto_debit")
    private String autoDebit;

    @JsonProperty("repay_mode")
    private String repayMode;

    @JsonProperty("auto_debit_bank_acct")
    private String autoDebitBankAcct;

    @JsonProperty("request_date")
    private String requestDate;

    @JsonProperty("completed_date")
    private String completedDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_register")
    private String userRegister;

}
