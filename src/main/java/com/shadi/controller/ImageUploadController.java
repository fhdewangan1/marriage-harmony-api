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
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadImages(@RequestParam("images") MultipartFile[] images,
            @RequestParam("mobileNumber") String mobileNumber) {
        try {
            List<Long> uploadedImageIds = imageUploadService.uploadImages(images, mobileNumber);
            String successMessage = "Successfully uploaded images with IDs: " + uploadedImageIds;

            // Returning success response with image IDs
            return ResponseEntity.ok(new ImageUploadResponse(true, successMessage, uploadedImageIds));
        } catch (Exception e) {
            String errorMessage = "Failed to upload images: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageUploadResponse(false, errorMessage, null));
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

            String successMessage = "Successfully retrieved images for mobile number: " + mobileNumber;
            return ResponseEntity.ok(new GetImageResponse(true, successMessage, images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GetImageResponse(false, "An unexpected error occurred.", null));
        }
    }

    @PutMapping("/edit-image/{id}")
    public ResponseEntity<UpdateImageResponse> updateImage(@PathVariable Long id, @RequestBody MultipartFile image) {
        try {
            boolean updated = imageUploadService.updateImage(id, image);
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

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<DeleteImageResponse> deleteImage(@PathVariable Long id) {
        try {
            boolean deleted = imageUploadService.deleteImage(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new DeleteImageResponse(false, "Image with ID " + id + " not found.", null));
            }
            return ResponseEntity.ok(new DeleteImageResponse(true, "Successfully deleted image with ID " + id, id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DeleteImageResponse(false, "Failed to delete image: " + e.getMessage(), null));
        }
    }
}
