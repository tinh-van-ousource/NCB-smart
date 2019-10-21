/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace
 *
 */
@Repository
public interface CityDao extends JpaRepository<City, String> {
	City findByProId(String proId);
}
	