package com.app.feature.notifications.repository;

import org.springframework.stereotype.Repository;

import com.app.feature.auth.model.User;
import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.model.NotificationType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByRecipientAndTypeNotOrderByCreationDateDesc(User recipient, NotificationType type);
}
