package com.shadi.service;

import java.util.Map;

import com.shadi.entity.SearchDetails;

public interface SearchDetailsService {

	public Map<String, Object> addSearchDetails(SearchDetails searchDetails);

	public Map<String, Object> getSearchDetails();
}
