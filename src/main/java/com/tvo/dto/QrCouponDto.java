package com.tvo.dto;

import com.tvo.request.UserCoupon;
import lombok.Data;

import java.util.List;

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

    private String startDate;

    private String endDate;

    private Float amount;

    private Float paymentMin;

    private Float amountMax;

    private Float amountPercentage;

    private Long numberPerCustomer;

    private Long totalNumberCoupon;

    private String status;

    private String approveStatus;

    private List<UserCoupon> userCoupons;

    private String deletedAt;

    private String createdAt;

    private String createdBy;

    private String updatedAt;

    private String updatedBy;
}
