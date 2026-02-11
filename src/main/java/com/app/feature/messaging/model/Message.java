package com.app.feature.messaging.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.app.feature.auth.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "messages")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", columnDefinition = "UUID")
    @JsonIgnoreProperties({ "email", "house" })
    private User sender;

    @CreationTimestamp
    private LocalDateTime sendTime;

    private MessageType type;

    private String content;

    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonIgnore
    private Conversation conversation;

    public Message() {
    }

    public Message(User sender, MessageType type, String content) {
        this.sender = sender;
        this.type = type;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
