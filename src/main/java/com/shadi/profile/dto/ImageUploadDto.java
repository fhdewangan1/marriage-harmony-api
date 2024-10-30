package com.shadi.profile.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadDto {
    private Long userId;
    private MultipartFile[] images;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
