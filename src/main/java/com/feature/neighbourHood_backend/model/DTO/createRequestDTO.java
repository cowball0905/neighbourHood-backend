package com.feature.neighbourHood_backend.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class createRequestDTO {
    private String title;
    private String content;
    private int redeemPoints;
    private MultipartFile[] files;

    public createRequestDTO() {
    }

    public createRequestDTO(String title, String content, int redeemPoints, MultipartFile[] files) {
        this.title = title;
        this.content = content;
        this.redeemPoints = redeemPoints;
        this.files = files;
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
}
