package com.tvo.dao;

import com.tvo.model.QrServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Repository
public interface QrServiceDao extends JpaRepository<QrServiceEntity, Long> {
    @Query("SELECT qs FROM QrServiceEntity qs WHERE qs.id = ?1 AND qs.deletedAt IS NULL")
    QrServiceEntity findByIdNotDeleted(Long id);
}
