package com.feature.neighbourHood_backend.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class createAnnouncementDTO {
    private String title;
    private String content;
    private MultipartFile[] files;

    public createAnnouncementDTO() {
    }

    public createAnnouncementDTO(String title, String content, MultipartFile[] files) {
        this.title = title;
        this.content = content;
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

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}