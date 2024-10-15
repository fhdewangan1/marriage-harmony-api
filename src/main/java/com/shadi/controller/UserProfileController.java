package com.shadi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadi.config.CustomAuthentication;
import com.shadi.profile.dto.UserRegistrationProfileDto;
import com.shadi.service.UserRegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
public class UserProfileController {

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private CustomAuthentication customAuthentication;

	@PostMapping("/create-profile")
	public ResponseEntity<Map<String, Object>> createUserProfile(@ModelAttribute UserRegistrationProfileDto dto) {
		return ResponseEntity.status(201).body(userRegistrationService.createProfile(dto));
	}

	@GetMapping("/login-profile")
	public ResponseEntity<Map<String, Object>> loginProfile(@RequestParam String mobileNumber,
			@RequestParam String password) {
		iwAuthentication(mobileNumber, password);
		return ResponseEntity.status(200).body(userRegistrationService.userLogin(mobileNumber, password));
	}

	@PutMapping("/update-profile")
	public ResponseEntity<Map<String, Object>> updateUserProfile(@ModelAttribute UserRegistrationProfileDto dto) {
		return ResponseEntity.status(200).body(userRegistrationService.updateProfile(dto));
	}

	private void iwAuthentication(String mobileNumber, String password) {
		UsernamePasswordAuthenticationToken userNamePasswordauth = new UsernamePasswordAuthenticationToken(mobileNumber,
				password);
		this.customAuthentication.authenticate(userNamePasswordauth);

	}


}
