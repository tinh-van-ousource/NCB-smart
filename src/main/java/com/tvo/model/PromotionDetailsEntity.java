package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author thanglt on 11/12/2020
 * @version 1.0
 */
@Entity
@Table(name = "PROMOTION_DETAILS")
@Data
public class PromotionDetailsEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PROMOTION_DETAILS")
    @SequenceGenerator(sequenceName = "PROMOTION_DETAILS_SQ", allocationSize = 1, name = "ID_PROMOTION_DETAILS")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROMOTION_NAME")
    private String promotionName;

    @Column(name = "PROMOTION_CODE")
    private String promotionCode;

    @Column(name = "REFER_PARTNERCODE")
    private String referPartnerCode;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "DELETED_AT")
    private Date deletedAt;

    @Column(name = "DELETED_BY")
    private String deletedBy;
}
