package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_FEE_MBAPP")
@Getter
@Setter
public class ProductFeeMbApp implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_PRODUCT_FREE_SQ")
  @SequenceGenerator(sequenceName = "AI_CMS_PRODUCT_FREE_SQ", allocationSize = 1, name = "AI_CMS_PRODUCT_FREE_SQ")
  @Column(name = "ID")
  private Long id;

  @Column(name = "GRPRD_ID")
  private String grprdId;

  @Column(name = "PRD_NAME")
  private String prdName;

  @Column(name = "FEE_AMOUNT")
  private String feeAmount;

  @Column(name = "FEE_MIN")
  private String feeMin;

  @Column(name = "FEE_MAX")
  private String feeMax;

  @Column(name = "PRD_CODE")
  private String prdCode;

  @Column(name = "FEE_TYPE")
  private String feeType;

  @Column(name = "CCY")
  private String ccy;

  @Column(name = "CODE_FEE")
  private String codeFee;

  @Column(name = "TAX_PERCENT")
  private Integer taxPercent;

  @Column(name = "CREATED_USER")
  private String createdUser;

  @Column(name = "CREATED_TIME")
  private Date createdTime;

}
