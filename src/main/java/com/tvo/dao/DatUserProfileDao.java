package com.tvo.dao;

import com.tvo.model.DatUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Jul 31, 2019
 */
@Repository
public interface DatUserProfileDao extends JpaRepository<DatUserProfile, String>, JpaSpecificationExecutor<DatUserProfile> {
	
}
