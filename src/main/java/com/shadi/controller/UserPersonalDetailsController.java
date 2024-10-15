package com.shadi.controller;

import com.shadi.entity.UserPersonalDetails;
import com.shadi.exception.GenericException;
import com.shadi.service.UserPersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserPersonalDetailsController {

	@Autowired
	private UserPersonalDetailsService userPersonalDetailsService;

	@PostMapping("/save-user-personal-details")
	public ResponseEntity<Map<String, Object>> saveUserPersonalDetails(
			@RequestBody UserPersonalDetails userPersonalDetails, @RequestParam String mobileNumber) {
		try {
			Map<String, Object> response = userPersonalDetailsService.saveUserPersonalDetails(userPersonalDetails,
					mobileNumber);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-personal-details")
	public ResponseEntity<Map<String, Object>> getUserPersonalDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Map<String, Object> response = userPersonalDetailsService.getUserPersonalDetails(page, size);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-personal-details/{id}")
	public ResponseEntity<Map<String, Object>> getUserPersonalDetailsById(@PathVariable Long id) {
		try {
			Map<String, Object> response = userPersonalDetailsService.getUserPersonalDetailsById(id);
			return response.containsKey("UserPersonalDetails") ? new ResponseEntity<>(response, HttpStatus.OK)
					: new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-user-personal-details/{mobileNumber}")
	public ResponseEntity<Map<String, Object>> updateUserPersonalDetails(@PathVariable String mobileNumber,
			@RequestBody UserPersonalDetails userPersonalDetails) {
		try {
			Map<String, Object> response = userPersonalDetailsService.updateUserPersonalDetails(userPersonalDetails,mobileNumber);
			return response.containsKey("UserPersonalDetails") ? new ResponseEntity<>(response, HttpStatus.OK)
					: new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
