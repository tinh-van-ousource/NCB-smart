package com.tvo.dao;

import com.tvo.model.PrdPromotionMbApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrdPromotionMbAppDAO extends JpaRepository<PrdPromotionMbApp, Long> {

    List<PrdPromotionMbApp> findByPrdAndAndProCode(String prd, String proCode);

}
