/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.ParCardProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace
 *
 */
@Repository
public interface ParCardProductDao extends JpaRepository<ParCardProduct, String>{
	public ParCardProduct findByPrdcode(String prdcode);
}
