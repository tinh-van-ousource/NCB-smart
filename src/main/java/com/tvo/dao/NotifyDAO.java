package com.tvo.dao;

import com.tvo.model.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface NotifyDAO extends JpaRepository<Notify, String>{
	 Notify findByType(String type);
	
	  void deleteNotifyByType(String type);
}
