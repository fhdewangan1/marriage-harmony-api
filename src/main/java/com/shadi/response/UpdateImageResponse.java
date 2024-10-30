package com.shadi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageResponse {
    private boolean success;
    private String message;
    private Long imageId; // Include the image ID that was updated
}
