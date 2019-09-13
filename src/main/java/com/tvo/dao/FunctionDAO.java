package com.tvo.dao;

import com.tvo.model.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionDAO extends JpaRepository<Function, Long>{
	public Function findByTypeId(String typeId);
}
