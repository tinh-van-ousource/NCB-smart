package com.tvo.dao;

import com.tvo.model.DatUserProfile;
import com.tvo.request.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Jul 31, 2019
 */
@Repository
public interface DatUserProfileDao extends JpaRepository<DatUserProfile, String>, JpaSpecificationExecutor<DatUserProfile> {
        @Query("SELECT dup.usrid FROM DatUserProfile dup")
        List<String> findAllUsrid();

        @Query("SELECT new com.tvo.request.UserCoupon(dup.usrid, dup.cifgrp) FROM DatUserProfile dup")
        List<UserCoupon> findAllUser();
}
