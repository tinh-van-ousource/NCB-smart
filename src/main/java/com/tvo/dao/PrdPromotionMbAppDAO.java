package com.tvo.dao;

import com.tvo.model.PrdPromotionMbApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrdPromotionMbAppDAO extends JpaRepository<PrdPromotionMbApp, Long> {

    List<PrdPromotionMbApp> findByPrdAndAndProCode(String prd, String proCode);

    @Query("SELECT DISTINCT pp.proCode FROM PrdPromotionMbApp pp WHERE pp.prd IS NOT NULL ORDER BY pp.proCode ASC")
    List<String> getAllProCode();

}
