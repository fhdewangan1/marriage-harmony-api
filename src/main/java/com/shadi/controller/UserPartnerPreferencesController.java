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

import com.shadi.entity.UserPartnerPreferences;
import com.shadi.exception.GenericException;
import com.shadi.service.UserPartnerPreferencesService;

@RestController
@RequestMapping("/api/v1")
public class UserPartnerPreferencesController {

	@Autowired
	private UserPartnerPreferencesService userPartnerPreferencesService;

	@PostMapping("/save-user-partner-preferences")
	public ResponseEntity<Map<String, Object>> saveUserPartnerPreferences(
			@RequestBody UserPartnerPreferences userPartnerPreferences, @RequestParam String mobileNumber) {
		try {
			Map<String, Object> response = userPartnerPreferencesService
					.saveUserPartnerPreferences(userPartnerPreferences, mobileNumber);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-partner-preferences")
	public ResponseEntity<Map<String, Object>> getUserPartnerPreferences(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Map<String, Object> response = userPartnerPreferencesService.getUserPartnerPreferences(page, size);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user-partner-preferences/{id}")
	public ResponseEntity<Map<String, Object>> getUserPartnerPreferencesById(@PathVariable Long id) {
		try {
			Map<String, Object> response = userPartnerPreferencesService.getUserPartnerPreferencesById(id);
			return response.containsKey("UserPartnerPreferences") ? new ResponseEntity<>(response, HttpStatus.OK)
					: new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-user-partner-preferences/{mobileNumber}")
	public ResponseEntity<Map<String, Object>> updateUserPersonalDetails(@PathVariable String mobileNumber,
			@RequestBody UserPartnerPreferences userPartnerPreferences) {
		try {
			Map<String, Object> response = userPartnerPreferencesService.updateUserPartnerPreferences(mobileNumber,
					userPartnerPreferences);
			return response.containsKey("UserPartnerPreferences") ? new ResponseEntity<>(response, HttpStatus.OK)
					: new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (GenericException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
