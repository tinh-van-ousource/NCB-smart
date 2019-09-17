package com.tvo.dao;

import com.tvo.model.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsDAO extends JpaRepository<Promotions, Long> {
    Promotions findByid(Long id);
}
