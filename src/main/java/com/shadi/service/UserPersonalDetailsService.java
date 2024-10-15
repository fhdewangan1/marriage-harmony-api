package com.shadi.service;

import java.util.Map;

import com.shadi.entity.UserPersonalDetails;

public interface UserPersonalDetailsService {

	public Map<String, Object> saveUserPersonalDetails(UserPersonalDetails userPersonalDetails, String mobileNumber);

	public Map<String, Object> getUserPersonalDetails(int page, int size);

	public Map<String, Object> getUserPersonalDetailsById(Long userPersonalId);

	public Map<String, Object> updateUserPersonalDetails(UserPersonalDetails userPersonalDetails, String mobileNumber);
}
