package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon {
    private String userName;
    private String userCif;
}
