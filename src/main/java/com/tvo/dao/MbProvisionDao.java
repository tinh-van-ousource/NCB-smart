package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tvo.model.MbProvision;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@Repository
public interface MbProvisionDao extends JpaRepository<MbProvision, String>, JpaSpecificationExecutor<MbProvision> {

}
