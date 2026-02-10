package com.app.feature.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feature.messaging.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
