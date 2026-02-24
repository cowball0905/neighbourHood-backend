package com.app.feature.rating.dto;

import java.util.UUID;

public class createRatingDTO {
    private UUID senderUUID;

    private UUID recipientUUID;

    private Long postID;

    private float marks;

    private String comment;

    public createRatingDTO() {
    }

    public createRatingDTO(UUID senderUUID, UUID recipientUUID, Long postID, float marks, String comment) {
        this.senderUUID = senderUUID;
        this.recipientUUID = recipientUUID;
        this.postID = postID;
        this.marks = marks;
        this.comment = comment;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public UUID getRecipientUUID() {
        return recipientUUID;
    }

    public Long getPostID() {
        return postID;
    }

    public float getMarks() {
        return marks;
    }

    public String getComment() {
        return comment;
    }

    public void setSenderUUID(UUID senderUUID) {
        this.senderUUID = senderUUID;
    }

    public void setRecipientUUID(UUID recipientUUID) {
        this.recipientUUID = recipientUUID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
