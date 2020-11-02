package com.tvo.dao;

import com.tvo.model.ReferFriendConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@Repository
public interface ReferFriendConfigurationDao extends JpaRepository<ReferFriendConfigurationEntity, Long> {
    @Query("SELECT rf FROM ReferFriendConfigurationEntity rf WHERE rf.id = ?1 AND rf.deletedAt IS NULL")
    ReferFriendConfigurationEntity findByIdNotDeleted(Long id);
}
