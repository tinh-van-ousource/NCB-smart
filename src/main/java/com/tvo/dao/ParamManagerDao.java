package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tvo.model.ParamManager;

@Repository
public interface ParamManagerDao extends JpaRepository<ParamManager, String>, JpaSpecificationExecutor<ParamManager> {

	public ParamManager findByParamNo(String paramNo);

}
