package com.shadi.service;

import com.shadi.entity.UserImage;
import com.shadi.repo.UserImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageUploadService {

    private final UserImageRepo userImageRepo;

    @Autowired
    public ImageUploadService(UserImageRepo userImageRepo) {
        this.userImageRepo = userImageRepo;
    }

    public List<Long> uploadImages(MultipartFile[] images, String mobileNumber) throws IOException {
        List<Long> imageIds = new ArrayList<>();

        for (MultipartFile image : images) {
            UserImage userImage = new UserImage();
            userImage.setMobileNumber(mobileNumber);
            userImage.setImage(image.getBytes());
            UserImage savedImage = userImageRepo.save(userImage);
            imageIds.add(savedImage.getId());
        }

        return imageIds;
    }

    public List<byte[]> getImagesByMobileNumber(String mobileNumber) {
        List<UserImage> userImages = userImageRepo.findByMobileNumber(mobileNumber);
        List<byte[]> images = new ArrayList<>();

        for (UserImage userImage : userImages) {
            images.add(userImage.getImage());
        }

        return images;
    }

    public boolean updateImage(Long id, MultipartFile image) throws IOException {
        Optional<UserImage> optionalImage = userImageRepo.findById(id);
        if (optionalImage.isPresent()) {
            UserImage userImage = optionalImage.get();
            userImage.setImage(image.getBytes()); // Update image bytes
            userImageRepo.save(userImage); // Save the updated user image
            return true; // Indicate that the update was successful
        }
        return false; // Indicate that the image was not found
    }

    public boolean deleteImage(Long id) {
        if (userImageRepo.existsById(id)) {
            userImageRepo.deleteById(id); // Delete the image from the repository
            return true; // Indicate that the deletion was successful
        }
        return false; // Indicate that the image was not found
    }
}
