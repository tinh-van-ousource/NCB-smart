package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "SERVICE_REGISTER_MBAPP")
@NoArgsConstructor
@Setter
@Getter
public class ServiceRegisterEntity implements Serializable {

    private static final long serialVersionUID = 7003030148868469074L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICE_REGISTER_ID_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "SERVICE_REGISTER_ID_SQ", allocationSize = 1, name = "SERVICE_REGISTER_ID_SQ_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "COMP_CODE")
    private String compCode;

    @Column(name = "COMP_NAME")
    private String compName;

    @Column(name = "CUSTOMERNAME")
    private String customerName;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "IDCARD")
    private String idCard;

    @Column(name = "IDCARD_ISSUEDATE")
    private Date idCardIssueDate;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "SERVICE")
    private String service;

    @Column(name = "CARDNO")
    private String cardNo;

    @Column(name = "REISSUE_REASON")
    private String reissueReason;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "CLASS")
    private String cardClass;

    @Column(name = "FEE")
    private Long fee;

    @Column(name = "REQUEST_DATE")
    private String requestDate;

    @Column(name = "COMPLETED_DATE")
    private String completedDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "USER_REGISTER")
    private String userRegister;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SALARY_CODE")
    private String salaryCode;

    @Column(name = "SALARY_BANK")
    private String salaryBank;

    @Column(name = "CREDITLIMIT")
    private Long creditLimit;

    @Column(name = "MONTHLY_INCOME")
    private Long monthlyIncome;

    @Column(name = "MONTHLY_SPEND")
    private Long monthlySpend;

    @Column(name = "AUTODEBIT")
    private String autoDebit;

    @Column(name = "REPAY_MODE")
    private String repayMode;

    @Column(name = "AUTODEBIT_BANKACCT")
    private String autoDebitBankAcct;

    @Column(name = "ACCTNO")
    private String acctNo;

    @Column(name = "USER_ID")
    private String userId;

}
