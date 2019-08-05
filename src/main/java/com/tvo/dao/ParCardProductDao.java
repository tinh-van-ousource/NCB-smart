/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.ParCardProduct;

/**
 * @author Ace
 *
 */
@Repository
public interface ParCardProductDao extends JpaRepository<ParCardProduct, String>{
	public ParCardProduct findByPrdcode(String prdcode);
}
