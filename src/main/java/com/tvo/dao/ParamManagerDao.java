package com.tvo.dao;

import com.tvo.model.ParamManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Repository
public interface ParamManagerDao extends JpaRepository<ParamManager, String>, JpaSpecificationExecutor<ParamManager> {

	public ParamManager findByParamNo(String paramNo);

}
