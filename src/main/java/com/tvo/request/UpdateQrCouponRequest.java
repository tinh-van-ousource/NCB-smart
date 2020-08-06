package com.tvo.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Data
public class UpdateQrCouponRequest {
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
}
