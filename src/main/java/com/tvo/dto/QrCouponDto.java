package com.tvo.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Data
public class QrCouponDto {
    private Long id;

    private String name;

    private String description;

    private String code;

    private String objectUserType;

    private String discountType;

    private String serviceId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Float amount;

    private Float paymentMin;

    private Float amountMax;

    private Float amountPercentage;

    private Long numberPerCustomer;

    private Long totalNumberCoupon;

    private String status;

    private String approveStatus;

    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
