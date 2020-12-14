package com.tvo.dao;

import com.tvo.model.QrCouponsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 8/6/2020
 * @version 1.0
 */
@Repository
public interface QrCouponDao extends JpaRepository<QrCouponsEntity, Long> {
    @Query("SELECT qc FROM QrCouponsEntity qc WHERE qc.id = ?1 AND qc.deletedAt IS NULL")
    QrCouponsEntity findByIdNotDeleted(Long id);

    @Query("SELECT qc FROM QrCouponsEntity qc WHERE UPPER(qc.code) = ?1")
    QrCouponsEntity findByCode(String id);

}
