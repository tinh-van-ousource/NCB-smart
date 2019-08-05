package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Function;

@Repository
public interface FunctionDAO extends JpaRepository<Function, String>{
	public Function findByTypeFunction(String typeFunction);
}
