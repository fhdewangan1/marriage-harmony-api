package com.shadi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; // Ensure this import is present
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadi.config.CustomAuthentication;
import com.shadi.profile.dto.UserRegistrationProfileDto;
import com.shadi.service.UserRegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
public class UserProfileController {

    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class); // Logger instance

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private CustomAuthentication customAuthentication;

    @PostMapping("/create-profile")
    public ResponseEntity<Map<String, Object>> createUserProfile(@RequestBody UserRegistrationProfileDto dto) {
        log.info("Received UserRegistrationProfileDto: {}", dto); // Log received DTO

        // Validate required fields
        if (dto.getMobileNumber() == null || dto.getFirstName() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mobile number and first name are required"));
        }

        // Call the service to create the profile
        Map<String, Object> response = userRegistrationService.createProfile(dto);
        log.info("User profile created: {}", response); // Log after profile creation
        return ResponseEntity.status(201).body(response); // Return response
    }

    @GetMapping("/login-profile")
    public ResponseEntity<Map<String, Object>> loginProfile(@RequestParam String mobileNumber,
            @RequestParam String password) {
        iwAuthentication(mobileNumber, password);
        return ResponseEntity.status(200).body(userRegistrationService.userLogin(mobileNumber, password));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestBody UserRegistrationProfileDto dto) {
        log.info("Updating user profile: {}", dto);
        return ResponseEntity.status(200).body(userRegistrationService.updateProfile(dto));
    }

    private void iwAuthentication(String mobileNumber, String password) {
        UsernamePasswordAuthenticationToken userNamePasswordauth = new UsernamePasswordAuthenticationToken(mobileNumber,
                password);
        this.customAuthentication.authenticate(userNamePasswordauth);
    }
}
