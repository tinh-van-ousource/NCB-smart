package com.tvo.dao;

import com.tvo.model.QrMerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 8/31/2020
 * @version 1.0
 */
@Repository
public interface QrMerchantDao extends JpaRepository<QrMerchantEntity, Long> {
    @Query("SELECT q FROM QrMerchantEntity q WHERE UPPER(q.name) = ?1")
    QrMerchantEntity findByName(String name);
}
