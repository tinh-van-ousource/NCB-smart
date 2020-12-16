package com.tvo.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * @author thanglt on 12/15/2020
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterDto {
    private Long id;

    private String type;

    private String compCode;

    private String compName;

    private String customerName;

    private String cif;

    private String idCard;

    private LocalDate idCardIssueDate;

    private String phone;

    private String service;

    private String cardNo;

    private String reissueReason;

    private String product;

    private String cardClass;

    private Long fee;

    private String requestDate;

    private String completedDate;

    private String status;

    private String userRegister;

    private String description;

    private String salaryCode;

    private String salaryBank;

    private Long creditLimit;

    private Long monthlyIncome;

    private Long monthlySpend;

    private String autoDebit;

    private String repayMode;

    private String autoDebitBankAcct;

    private String acctNo;

    private String userId;
}
