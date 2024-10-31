package com.shadi.profile.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadDto {
    private Long userId;
    private MultipartFile[] images;

    public ImageUploadDto() {
        images = new MultipartFile[10];
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MultipartFile getImage(int index) {
        validateIndex(index);
        return images[index - 1];
    }

    public void setImage(int index, MultipartFile image) {
        validateIndex(index);
        images[index - 1] = image;
    }

    private void validateIndex(int index) {
        if (index < 1 || index > 10) {
            throw new IndexOutOfBoundsException("Index must be between 1 and 10");
        }
    }
}
