package com.tvo.dao;

import com.tvo.model.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionDAO extends JpaRepository<Function, Long>{
	public Function findByTypeId(String typeId);
	public Function findByPrdName(String prdName);
	public Function findByPrd(String prd);
	public Function findByid(Long id);

	@Query("SELECT DISTINCT f.prdName FROM Function f WHERE f.prd IS NOT NULL ORDER BY f.prdName ASC")
	List<String> getAllPrdName();
}
