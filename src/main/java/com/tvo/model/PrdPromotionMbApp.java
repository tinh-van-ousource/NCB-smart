package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "PRD_PROMOTION_MBAPP")
public class PrdPromotionMbApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_PRD_PROMOTION_SQ")
    @SequenceGenerator(sequenceName = "AI_CMS_PRD_PROMOTION_SQ", allocationSize = 1, name = "AI_CMS_PRD_PROMOTION_SQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRD")
    private String prd;

    @Column(name = "PRO_CODE")
    private String proCode;

    @Column(name = "TRAN_TYPE")
    private String tranType;

    @Column(name = "TYPE_ID")
    private String typeId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CCY")
    private String ccy;

    @Column(name = "PERCENTAGE")
    private Integer percentage;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

}
