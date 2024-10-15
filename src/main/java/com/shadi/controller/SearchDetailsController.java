package com.shadi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadi.entity.SearchDetails;
import com.shadi.exception.GenericException;
import com.shadi.service.SearchDetailsService;

@RestController
@RequestMapping("/api/v1/admin")
public class SearchDetailsController {

	@Autowired
	public SearchDetailsService searchDetailsService;

	@PostMapping("/insert-search-data")
	public ResponseEntity<Map<String, Object>> insertSearchDetails(@RequestBody SearchDetails searchDetails) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("insert data");
			map = searchDetailsService.addSearchDetails(searchDetails);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get-search-data")
	public ResponseEntity<Map<String, Object>> getAllSearchDetails() {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("get data");
			map = searchDetailsService.getSearchDetails();
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return ResponseEntity.ok().body(map);
	}
}
