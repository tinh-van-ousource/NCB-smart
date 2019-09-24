package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GROUP_FUNCTION_MBAPP")
@Getter
@Setter
public class Promotions {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_FUNCTION_SQ")
    @SequenceGenerator(sequenceName = "AI_CMS_FUNCTION_SQ", allocationSize = 1, name = "AI_CMS_FUNCTION_SQ")
    private Long id;

    @Column(name = "TRAN_TYPE")
    private String tranType;

    @Column(name = "TYPE_ID")
    private String typeId;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "PROMOTION")
    private String promotion;

    @Column(name = "PROMOTION_NAME")
    private String promotionName;

    @Column(name = "PERCENTAGE")
    private String percentage;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "PRD_NAME")
    private String prdName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "PRD")
    private String prd;

}
