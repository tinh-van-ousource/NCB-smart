/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tvo.model.City;
import com.tvo.model.Transaction;
import com.tvo.model.User;

/**
 * @author Ace
 *
 */
@Repository
public interface CityDao extends JpaRepository<City, Long> {
	City findByCityName(String cityName);
}
	