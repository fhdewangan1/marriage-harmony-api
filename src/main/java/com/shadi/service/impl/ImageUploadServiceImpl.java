package com.shadi.service.impl;

import com.shadi.entity.UserImageUploadEntity;
import com.shadi.repo.UserImageUploadRepo;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Autowired
    private UserImageUploadRepo userImageRepo;

    @Autowired
    private UserProfileRegistrationRepo userProfileRegistrationRepo;

    @Override
    public Long uploadImages(MultipartFile image1, MultipartFile image2, MultipartFile image3,
            MultipartFile image4, MultipartFile image5, MultipartFile image6,
            MultipartFile image7, MultipartFile image8, MultipartFile image9,
            MultipartFile image10, String mobileNumber) throws IOException {

        if (!userExists(mobileNumber)) {
            System.out.println("No user found with mobile number: " + mobileNumber);
            throw new RuntimeException("No user found with mobile number: " + mobileNumber);
        }

        UserImageUploadEntity userImage = new UserImageUploadEntity();
        userImage.setMobileNumber(mobileNumber);

        MultipartFile[] images = { image1, image2, image3, image4, image5, image6, image7, image8, image9, image10 };
        List<Long> imageIds = new ArrayList<>(); // To store IDs of saved images

        for (int i = 0; i < images.length; i++) {
            if (images[i] != null && !images[i].isEmpty()) {
                // Get the content type
                String contentType = images[i].getContentType();

                // Validate image format and size
                if ((contentType != null && !contentType.equals("image/jpeg") && !contentType.equals("image/png")) ||
                        images[i].getSize() > 5 * 1024 * 1024) {
                    System.out.println("Image " + (i + 1) + " is invalid: format or size.");
                    continue; // Skip invalid images
                }

                byte[] imageBytes = images[i].getBytes();
                System.out.println("Received image " + (i + 1) + ": " + images[i].getOriginalFilename() + " (size: "
                        + images[i].getSize() + ")");
                switch (i) {
                    case 0 -> userImage.setImage1(imageBytes);
                    case 1 -> userImage.setImage2(imageBytes);
                    case 2 -> userImage.setImage3(imageBytes);
                    case 3 -> userImage.setImage4(imageBytes);
                    case 4 -> userImage.setImage5(imageBytes);
                    case 5 -> userImage.setImage6(imageBytes);
                    case 6 -> userImage.setImage7(imageBytes);
                    case 7 -> userImage.setImage8(imageBytes);
                    case 8 -> userImage.setImage9(imageBytes);
                    case 9 -> userImage.setImage10(imageBytes);
                }
            } else {
            }
        }

        // Save image after setting all fields
        imageIds.add(userImageRepo.save(userImage).getId());
        UserImageUploadEntity savedImage = userImageRepo.save(userImage);
        return savedImage.getId();
    }

    @Override
    public List<byte[]> getImagesByMobileNumber(String mobileNumber) {
        System.out.println("Fetching images for mobile number: " + mobileNumber);

        // Fetch the user images from the repository
        List<UserImageUploadEntity> userImages = userImageRepo.findByMobileNumber(mobileNumber);

        if (userImages.isEmpty()) {
            System.out.println("No images found for mobile number: " + mobileNumber);
            return List.of(); // Return an empty list if no images found
        }

        // Extract images and filter out null values
        List<byte[]> imageList = userImages.stream()
                .flatMap(userImage -> Stream.<byte[]>of(
                        userImage.getImage1(),
                        userImage.getImage2(),
                        userImage.getImage3(),
                        userImage.getImage4(),
                        userImage.getImage5(),
                        userImage.getImage6(),
                        userImage.getImage7(),
                        userImage.getImage8(),
                        userImage.getImage9(),
                        userImage.getImage10()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println("Fetched " + imageList.size() + " images for mobile number: " + mobileNumber);
        return imageList;
    }

    @Override
    public boolean userExists(String mobileNumber) {
        boolean exists = userProfileRegistrationRepo.existsByMobileNumber(mobileNumber);
        return exists;
    }

    @Override
    public boolean updateImage(Long id, int imageIndex, MultipartFile image) throws IOException {
        Optional<UserImageUploadEntity> optionalImage = userImageRepo.findById(id);
        if (optionalImage.isPresent() && image != null && !image.isEmpty()) {
            UserImageUploadEntity userImage = optionalImage.get();
            switch (imageIndex) {
                case 1 -> userImage.setImage1(image.getBytes());
                case 2 -> userImage.setImage2(image.getBytes());
                case 3 -> userImage.setImage3(image.getBytes());
                case 4 -> userImage.setImage4(image.getBytes());
                case 5 -> userImage.setImage5(image.getBytes());
                case 6 -> userImage.setImage6(image.getBytes());
                case 7 -> userImage.setImage7(image.getBytes());
                case 8 -> userImage.setImage8(image.getBytes());
                case 9 -> userImage.setImage9(image.getBytes());
                case 10 -> userImage.setImage10(image.getBytes());
                default -> throw new IllegalArgumentException("Invalid image index: " + imageIndex);
            }
            userImageRepo.save(userImage);
            System.out.println("Image updated successfully for ID: " + id);
            return true;
        } else {
            System.out.println("Image not found or is empty for ID: " + id);
        }
        return false;
    }

    @Override
    public boolean deleteImage(Long id, int imageIndex) {
        Optional<UserImageUploadEntity> optionalImage = userImageRepo.findById(id);
        if (optionalImage.isPresent()) {
            UserImageUploadEntity userImage = optionalImage.get();
            if (imageIndex < 1 || imageIndex > 10) {
                throw new IllegalArgumentException("Invalid image index: " + imageIndex);
            }
            // Use a helper method to set the image to null
            deleteImageByIndex(userImage, imageIndex);
            userImageRepo.save(userImage);
            System.out.println("Image with ID: " + id + " and index: " + imageIndex + " deleted successfully.");
            return true;
        } else {
            System.out.println("Image with ID: " + id + " not found.");
        }
        return false;
    }

    private void deleteImageByIndex(UserImageUploadEntity userImage, int imageIndex) {
        switch (imageIndex) {
            case 1 -> userImage.setImage1(null);
            case 2 -> userImage.setImage2(null);
            case 3 -> userImage.setImage3(null);
            case 4 -> userImage.setImage4(null);
            case 5 -> userImage.setImage5(null);
            case 6 -> userImage.setImage6(null);
            case 7 -> userImage.setImage7(null);
            case 8 -> userImage.setImage8(null);
            case 9 -> userImage.setImage9(null);
            case 10 -> userImage.setImage10(null);
        }
    }
}
