package com.tvo.request;

import lombok.Data;

import java.util.List;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Data
public class CreateUserCouponRequest {
    private List<UserCoupon> userCouponList;
}
