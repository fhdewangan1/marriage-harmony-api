package com.shadi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadi.entity.UserFamilyDetails;
import com.shadi.service.UserFamilyDetailsService;

@RestController
@RequestMapping("/api/v1")
public class UserFamilyDetailsController {

	@Autowired
	private UserFamilyDetailsService userFamilyDetailsService;

	@GetMapping("/user-family-details")
	public ResponseEntity<Map<String, Object>> getUserFamilyDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Map<String, Object> response = userFamilyDetailsService.getUserFamilylDetails(page, size);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			// Log the exception if needed
			return new ResponseEntity<>(Map.of("error", "An error occurred while fetching user family details"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-family-details/{id}")
	public ResponseEntity<Map<String, Object>> getUserFamilylDetailsById(@PathVariable("id") Long userFamilyId) {
		try {
			Map<String, Object> response = userFamilyDetailsService.getUserFamilylDetailsById(userFamilyId);
			if (response.containsKey("Error")) {
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("error", "Error while fetching user family details by ID"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save-user-family-details")
	public ResponseEntity<Map<String, Object>> saveUserFamilylDetails(
			@RequestBody UserFamilyDetails userFamilyDetails, @RequestParam String mobileNumber) {
		try {
			Map<String, Object> response = userFamilyDetailsService.saveUserFamilylDetails(userFamilyDetails,mobileNumber);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("error", "Error while saving user family details"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-user-family-details/{mobileNumber}")
	public ResponseEntity<Map<String, Object>> updateUserFamilylDetails(@PathVariable String mobileNumber,
			@RequestBody UserFamilyDetails updatedDetails) {
		try {
			Map<String, Object> response = userFamilyDetailsService.updateUserFamilylDetails(mobileNumber, updatedDetails);
			if (response.containsKey("Error")) {
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("error", "Error while updating user family details"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
