package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@Entity
@Table(name = "QR_MERCHANT")
@Data
public class QrMerchantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QR_MERCHANT")
    @SequenceGenerator(sequenceName = "QR_MERCHANT_SEQ", allocationSize = 1, name = "ID_QR_MERCHANT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PLACE")
    private String place;

    @Column(name = "STORE_CODE")
    private String storeCode;

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
