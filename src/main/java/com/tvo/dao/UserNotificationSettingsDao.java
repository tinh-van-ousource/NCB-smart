package com.tvo.dao;

import com.tvo.model.UserNotificationSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nguyen Hoang Long on 9/15/2020
 * @created 9/15/2020
 * @project NCB-smart
 */
@Repository
public interface UserNotificationSettingsDao extends JpaRepository<UserNotificationSettingsEntity,Long> {
}
