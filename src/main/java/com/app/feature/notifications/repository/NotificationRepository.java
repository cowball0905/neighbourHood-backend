package com.app.feature.notifications.repository;

import org.springframework.stereotype.Repository;

import com.app.feature.notifications.model.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{

    
}
