package com.tvo.dao;

import com.tvo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("select u from User u where u.userName = ?1")
	User findByUserName(String userName);

}
