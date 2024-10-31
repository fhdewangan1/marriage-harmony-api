package com.shadi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadService {
    Long uploadImages(MultipartFile image1, MultipartFile image2, MultipartFile image3,
            MultipartFile image4, MultipartFile image5, MultipartFile image6,
            MultipartFile image7, MultipartFile image8, MultipartFile image9,
            MultipartFile image10, String mobileNumber) throws IOException;

    List<byte[]> getImagesByMobileNumber(String mobileNumber);

    boolean userExists(String mobileNumber);

    boolean updateImage(Long id, int imageIndex, MultipartFile image) throws IOException;

    boolean deleteImage(Long id, int imageIndex);
}
