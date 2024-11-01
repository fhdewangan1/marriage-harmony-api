package com.shadi.controller;

import com.shadi.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.shadi.response.ImageUploadResponse;
import com.shadi.response.GetImageResponse;
import com.shadi.response.UpdateImageResponse;
import com.shadi.response.DeleteImageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class UserImageUploadController {
    private final ImageUploadService imageUploadService;

    @Autowired
    public UserImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadImages(
            @RequestParam(value = "image_1", required = false) MultipartFile image1,
            @RequestParam(value = "image_2", required = false) MultipartFile image2,
            @RequestParam(value = "image_3", required = false) MultipartFile image3,
            @RequestParam(value = "image_4", required = false) MultipartFile image4,
            @RequestParam(value = "image_5", required = false) MultipartFile image5,
            @RequestParam(value = "image_6", required = false) MultipartFile image6,
            @RequestParam(value = "image_7", required = false) MultipartFile image7,
            @RequestParam(value = "image_8", required = false) MultipartFile image8,
            @RequestParam(value = "image_9", required = false) MultipartFile image9,
            @RequestParam(value = "image_10", required = false) MultipartFile image10,
            @RequestParam("mobileNumber") String mobileNumber) {
        try {
            if (!imageUploadService.userExists(mobileNumber)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ImageUploadResponse(false, "No user found with mobile number: " + mobileNumber,
                                null));
            }

            Long uploadedImageId = imageUploadService.uploadImages(image1, image2, image3, image4, image5, image6,
                    image7, image8, image9, image10, mobileNumber);
            return ResponseEntity.ok(new ImageUploadResponse(true,
                    "Successfully uploaded images with ID: " + uploadedImageId, List.of(uploadedImageId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ImageUploadResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse(false, "Failed to upload images: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<GetImageResponse> getUserImagesByMobileNumber(@PathVariable String mobileNumber) {
        try {
            List<byte[]> images = imageUploadService.getImagesByMobileNumber(mobileNumber);
            if (images.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new GetImageResponse(false, "No images found for mobile number: " + mobileNumber, null));
            }
            return ResponseEntity.ok(new GetImageResponse(true,
                    "Successfully retrieved images for mobile number: " + mobileNumber, images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GetImageResponse(false, "An unexpected error occurred.", null));
        }
    }

    @PutMapping("/edit-image/{id}/{imageIndex}")
    public ResponseEntity<UpdateImageResponse> updateImage(@PathVariable Long id, @PathVariable int imageIndex,
            @RequestParam MultipartFile image) {
        try {
            boolean updated = imageUploadService.updateImage(id, imageIndex, image);
            if (!updated) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new UpdateImageResponse(false, "Image with ID " + id + " not found.", null));
            }
            return ResponseEntity.ok(new UpdateImageResponse(true, "Successfully updated image with ID " + id, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateImageResponse(false, "Failed to update image: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-image/{id}/{imageIndex}")
    public ResponseEntity<DeleteImageResponse> deleteImage(@PathVariable Long id, @PathVariable int imageIndex) {
        try {
            boolean deleted = imageUploadService.deleteImage(id, imageIndex);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new DeleteImageResponse(false,
                                "Image with ID " + id + " and index " + imageIndex + " not found.", null));
            }
            return ResponseEntity.ok(new DeleteImageResponse(true,
                    "Successfully deleted image with ID " + id + " and index " + imageIndex, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DeleteImageResponse(false, "Failed to delete image: " + e.getMessage(), null));
        }
    }

}
