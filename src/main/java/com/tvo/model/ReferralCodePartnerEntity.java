package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author thanglt on 11/12/2020
 * @version 1.0
 */
@Entity
@Table(name = "REFERRAL_CODE_PARTNER")
@Data
public class ReferralCodePartnerEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_REFERRAL_CODE_PARTNER")
    @SequenceGenerator(sequenceName = "REFERRAL_CODE_PARTNER_SQ", allocationSize = 1, name = "ID_REFERRAL_CODE_PARTNER")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARTNER_NAME")
    private String partnerName;

    @Column(name = "PARTNER_CODE")
    private String partnerCode;

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
