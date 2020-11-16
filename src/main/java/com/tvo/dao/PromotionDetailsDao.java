package com.tvo.dao;

import com.tvo.model.PromotionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Repository
public interface PromotionDetailsDao extends JpaRepository<PromotionDetailsEntity, Long> {
    @Query("SELECT pd FROM PromotionDetailsEntity pd WHERE pd.id = ?1 AND pd.deletedAt IS NULL")
    PromotionDetailsEntity findByIdNotDeleted(Long id);
    @Query("SELECT pd FROM PromotionDetailsEntity pd WHERE pd.promotionCode = ?1 AND pd.deletedAt IS NULL")
    PromotionDetailsEntity findByPromotionCodeNotDeleted(String promotionCode);
}
