package com.feature.neighbourHood_backend.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class createPostDTO {
    private String title;
    private String content;
    private int type;
    private int redeemPoints;
    private int request_type;
    private int payment_method;
    private MultipartFile[] files;
    private boolean is_important;

    public createPostDTO() {
    }

    public createPostDTO(String title, String content, int type, int redeemPoints, MultipartFile[] files,
            int request_type,
            int payment_method, boolean is_important) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.redeemPoints = redeemPoints;
        this.files = files;
        this.request_type = request_type;
        this.payment_method = payment_method;
        this.is_important = is_important;
    }

    public int getType() {
        return this.type;
    }

    public int getRequestType() {
        return this.request_type;
    }

    public int getPaymentMethod() {
        return this.payment_method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRedeemPoints() {
        return redeemPoints;
    }

    public void setRedeemPoints(int redeemPoints) {
        this.redeemPoints = redeemPoints;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public boolean isImportant() {
        return this.is_important;
    }

    public boolean getIsImportant() {
        return this.is_important;
    }
}
