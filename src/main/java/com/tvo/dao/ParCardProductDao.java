/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.ParCardProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace
 *
 */
@Repository
public interface ParCardProductDao extends JpaRepository<ParCardProductEntity, String>{
	public ParCardProductEntity findByPrdcode(String prdcode);
}
