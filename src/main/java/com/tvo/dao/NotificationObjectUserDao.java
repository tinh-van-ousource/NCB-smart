package com.tvo.dao;

import com.tvo.model.NotificationObjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Nguyen Hoang Long on 9/14/2020
 * @created 9/14/2020
 * @project NCB-smart
 */
@Repository
public interface NotificationObjectUserDao extends JpaRepository<NotificationObjectUserEntity,Long> {
    @Query("SELECT noti FROM NotificationObjectUserEntity noti where noti.notificationId = ?1")
    List<NotificationObjectUserEntity> findByNotificationId(Long notificationId);
}
