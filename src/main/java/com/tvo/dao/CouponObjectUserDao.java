package com.tvo.dao;

import com.tvo.model.CouponObjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
public interface CouponObjectUserDao extends JpaRepository<CouponObjectUserEntity, Long> {
    @Query("SELECT cou FROM CouponObjectUserEntity cou WHERE cou.qrCouponId = ?1")
    List<CouponObjectUserEntity> findByQrCouponId(Long qrCouponId);
}
