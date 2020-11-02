package com.tvo.dao;

import com.tvo.model.ReferFriendConfigurationEntity;
import com.tvo.model.ReferFriendRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Repository
public interface ReferFriendRegistrationDao extends JpaRepository<ReferFriendRegistrationEntity, Long> {
}
