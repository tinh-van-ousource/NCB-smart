package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Entity
@Table(name = "QR_COUPONS")
@Data
public class QrCouponsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QR_COUPONS")
    @SequenceGenerator(sequenceName = "QR_COUPONS_SEQ", allocationSize = 1, name = "ID_QR_COUPONS")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private String code;

    @Column(name = "OBJECT_USER_TYPE")
    private String objectUserType;

    @Column(name = "DISCOUNT_TYPE")
    private String discountType;

    @Column(name = "SERVICE_ID")
    private String serviceId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "PAYMENT_MIN")
    private Float paymentMin;

    @Column(name = "AMOUNT_MAX")
    private Float amountMax;

    @Column(name = "AMOUNT_PERCENTAGE")
    private Float amountPercentage;

    @Column(name = "NUMBER_PER_CUSTOMER")
    private Long numberPerCustomer;

    @Column(name = "TOTAL_NUMBER_COUPON")
    private Long totalNumberCoupon;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "APPROVE_STATUS")
    private String approveStatus;

    @Column(name = "DELETED_AT")
    private Date deletedAt;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
