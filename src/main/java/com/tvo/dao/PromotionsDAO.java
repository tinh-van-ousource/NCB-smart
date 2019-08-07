package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Function;
import com.tvo.model.Promotions;

@Repository
public interface PromotionsDAO extends JpaRepository<Promotions, String> {
	public Promotions findByType(String type);
}
