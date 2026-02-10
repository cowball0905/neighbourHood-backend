package com.app.feature.messaging.dto;

import java.util.UUID;

import com.app.feature.messaging.model.MessageType;

public class createConversationDTO {
    private UUID recipientUuid;
    private Long post;
    private String message;
    private MessageType type;

    public UUID getRecipientUuid() {
        return recipientUuid;
    }

    public void setRecipientUuid(UUID recipientUuid) {
        this.recipientUuid = recipientUuid;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
