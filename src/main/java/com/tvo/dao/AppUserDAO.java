/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tvo.model.User;


/**
 * @author Ace
 *
 */
@Repository
public interface AppUserDAO extends JpaRepository<User, Long>{
	@Query("select u from User u where u.userName = ?1")
	User findByUserName(String userName);
	
}
