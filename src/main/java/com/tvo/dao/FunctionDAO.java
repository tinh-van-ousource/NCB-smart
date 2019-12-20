package com.tvo.dao;

import com.tvo.model.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionDAO extends JpaRepository<Function, Long>{

	Function findByTypeId(String typeId);
	Function findByPrdName(String prdName);
	
	Function findByPrd(String prd);
	
	@Query("SELECT f FROM Function f WHERE f.prd = :prd AND f.prdName = :prdName AND f.tranType = :tranType")
	Function findByPrdAndPrdNameAndTranType(String prd, String prdName, String tranType);
	
	@Query("SELECT DISTINCT f.prdName FROM Function f WHERE f.prd IS NOT NULL ORDER BY f.prdName ASC")
	List<String> getAllPrdName();

	@Query("SELECT DISTINCT f.prd, f.prdName FROM Function f ORDER BY f.prd ASC")
	List<Object> getAllPrdAndPrdName();

	List<Function> findListByPrd(String prd);
}
