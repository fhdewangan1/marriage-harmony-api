package com.shadi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadi.entity.SearchDetails;
import com.shadi.exception.GenericException;
import com.shadi.repo.SearchDetailsRepo;
import com.shadi.service.SearchDetailsService;

@Service
public class SearchDetailsServiceImpl implements SearchDetailsService {

	@Autowired
	private SearchDetailsRepo searchDetailsRepo;

	@Override
	public Map<String, Object> addSearchDetails(SearchDetails searchDetails) {
	    Map<String, Object> map = new HashMap<>();
	    try {
	        // Check if the entry already exists based on motherTongue, gender, and religion
	        SearchDetails existingDetails = searchDetailsRepo.findByMotherTongueAndGenderAndReligion(
	                searchDetails.getMotherTongue(),
	                searchDetails.getGender(),
	                searchDetails.getReligion());

	        if (existingDetails != null) {
	            map.put("res", "Data already exists with the same details");
	            map.put("details", existingDetails);
	        } else {
	            // If not found, proceed with saving the new details
	            searchDetails.setSId(UUID.randomUUID().toString());
	            searchDetailsRepo.save(searchDetails);
	            map.put("res", "Data Inserted Successfully");
	            map.put("details", searchDetails);
	        }
	    } catch (Exception e) {
	        map.put("res", "Data Insertion Failed");
	        throw new GenericException(e);
	    }
	    return map;
	}


	@Override
	public Map<String, Object> getSearchDetails() {
		Map<String, Object> map = new HashMap<>();
		List<SearchDetails> searchDetails = searchDetailsRepo.findAll();
		try {
			if (!searchDetails.isEmpty()) {
				map.put("details", searchDetails);
			} else {
				map.put("details", "No Data Available");
			}
		} catch (Exception e) {
			map.put("Error", e);
			throw new GenericException(e);
		}
		return map;
	}

}
