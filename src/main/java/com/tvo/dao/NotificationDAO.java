package com.tvo.dao;

import com.tvo.model.NotificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationDAO extends JpaRepository<NotificationsEntity,Long> {
    @Query("SELECT n FROM NotificationsEntity n WHERE n.id = ?1 AND n.deletedAt IS NULL")
    NotificationsEntity findByIdNotDeleted(Long id);
}
