package com.shadi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetImageResponse {
    private boolean success;
    private String message;
    private List<byte[]> images; // List of images in byte array format
}
