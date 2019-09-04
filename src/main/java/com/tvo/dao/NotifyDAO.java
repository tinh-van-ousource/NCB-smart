package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Notify;
import com.tvo.model.Promotions;
@Repository
public interface NotifyDAO extends JpaRepository<Notify, String>{
	public Notify findByType(String type);
}
