package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRegisterGetDetailResDto {
    private Long id;

    private String customerName;

    private String idCard;

    private String phone;

    private String service;

    private String compName;

    private String status;
    
    private String compCode;

    private List<ServiceRegisterLogResDto> serviceRegisterLogResDtoList;
    
    private String userId;
    
    private String requestDate;
    
    private String cif;
    
    private String userRegister;
    
    private String description;
    
    private String completedDate;
    
    private String autoDebitBankAcct;
    
    private String repayMode;
    
    private Long monthlySpend;
    
    private Long monthlyIncome;
    
    private Long creditLimit;
    
    private String salaryCode;
    
    private String salaryBank;
    
    private Long fee;
    
    private String cardClass;
    
    private String acctNo;
    
    private String product;
    
    private String reissueReason;
    
    private LocalDate idCardIssueDate;
    
    private String cardNo;
    

}
