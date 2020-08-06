package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Entity
@Table(name = "QR_COUPON_REDEEMED")
@Data
public class QrCouponRedeemedEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QR_COUPON_REDEEMED")
    @SequenceGenerator(sequenceName = "QR_COUPON_REDEEMED_SEQ", allocationSize = 1, name = "ID_QR_COUPON_REDEEMED")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "QR_COUPON_ID")
    private Long qrCouponId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_CIF")
    private String userCif;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
