package com.shadi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {
    private boolean success;
    private String message;
    private List<Long> imageIds;
}
