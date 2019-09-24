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
public interface CityDao extends JpaRepository<City, Long> {
	City findByCityId(Long id);
	City findByCityCode(String cityCode);
}
	