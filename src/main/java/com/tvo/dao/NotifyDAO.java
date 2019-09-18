package com.tvo.dao;

import com.tvo.model.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NotifyDAO extends JpaRepository<Notify, String>{
	 Notify findByType(String type);
	
	  void deleteNotifyByType(String type);
}
