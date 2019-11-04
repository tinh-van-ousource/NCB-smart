package com.tvo.dao;

import com.tvo.model.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionsDAO extends JpaRepository<Promotions, String> {

	Promotions findByProCode(String proCode);

	@Query("SELECT DISTINCT p.proCode FROM Promotions p ORDER BY p.proCode")
	List<String> getDistinctByProCode();
}
