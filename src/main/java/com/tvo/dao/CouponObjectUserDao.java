package com.tvo.dao;

import com.tvo.model.CouponObjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
public interface CouponObjectUserDao extends JpaRepository<CouponObjectUserEntity, Long> {

}
