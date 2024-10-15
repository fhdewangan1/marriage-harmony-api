package com.shadi.service;

import java.util.Map;

import com.shadi.entity.UserPartnerPreferences;

public interface UserPartnerPreferencesService {

	public Map<String, Object> saveUserPartnerPreferences(UserPartnerPreferences userPartnerPreferences,
			String mobileNumber);

	public Map<String, Object> getUserPartnerPreferences(int page, int size);

	public Map<String, Object> getUserPartnerPreferencesById(Long userLifeStyleAndEducationId);

	public Map<String, Object> updateUserPartnerPreferences(String mobileNumber,
			UserPartnerPreferences userPartnerPreferences);

}
