package com.shadi.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shadi.entity.UserPartnerPreferences;
import com.shadi.exception.GenericException;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.repo.UserPartnerPreferencesRepo;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.UserPartnerPreferencesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserPartnerPreferencesServiceImpl implements UserPartnerPreferencesService {

	@Autowired
	private UserPartnerPreferencesRepo userPartnerPreferencesRepo;
	@Autowired
	private UserProfileRegistrationRepo userProfileRegistrationRepo;

	@Override
	public Map<String, Object> saveUserPartnerPreferences(UserPartnerPreferences userPartnerPreferences,
			String mobileNumber) {
		Map<String, Object> map = new HashMap<>();
		UserRegistrationProfile findByMobileNumber = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
		try {
			if (findByMobileNumber == null) {
				throw new GenericException("Profile Not Avaliable with mobile number :" + mobileNumber);
			} else {
				userPartnerPreferences.setPreferredLocation(userPartnerPreferences.getPreferredLocation());
				userPartnerPreferences.setAnyOtherPreferences(userPartnerPreferences.getAnyOtherPreferences());
				userPartnerPreferences.setDesiredJobValue(userPartnerPreferences.getDesiredJobValue());
				userPartnerPreferences.setFamilyStatus(userPartnerPreferences.getFamilyStatus());
				userPartnerPreferences.setFamilyValue(userPartnerPreferences.getFamilyValue());
				userPartnerPreferences.setUserRegistrationProfile(findByMobileNumber);
				UserPartnerPreferences details = userPartnerPreferencesRepo.save(userPartnerPreferences);
				map.put("UserPartnerPreferences", details);
				map.put("status", HttpStatus.OK.value());
			}
		} catch (Exception e) {
			log.error("Exception in userPartnerPreferences  " + e.getMessage(), e);
			throw new GenericException("Error While Saving Details");
		}
		return map;
	}

	@Override
	public Map<String, Object> getUserPartnerPreferences(int page, int size) {
		Map<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, size);

			Page<UserPartnerPreferences> userPartnerPreferences = userPartnerPreferencesRepo.findAll(pageable);

			if (userPartnerPreferences.getContent().isEmpty()) {
				map.put("message", "No data found");
				map.put("GetUserPartnerPreferences", Collections.emptyList());
				map.put("status", HttpStatus.NOT_FOUND.value());
			} else {
				map.put("GetUserPartnerPreferences", userPartnerPreferences.getContent());
				map.put("status", HttpStatus.OK.value());
			}
			map.put("totalPages", userPartnerPreferences.getTotalPages());
			map.put("totalElements", userPartnerPreferences.getTotalElements());
			map.put("currentPage", userPartnerPreferences.getNumber());
			map.put("pageSize", userPartnerPreferences.getSize());
		} catch (Exception e) {
			log.error("Exception in GetUserPartnerPreferences: " + e.getMessage(), e);
			throw new GenericException("Error while fetching user partner preferences");
		}

		return map;
	}

	@Override
	public Map<String, Object> getUserPartnerPreferencesById(Long userPartnerPreferencesId) {
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<UserPartnerPreferences> userPartnerPreferences = userPartnerPreferencesRepo
					.findById(userPartnerPreferencesId);

			if (userPartnerPreferences.isPresent()) {
				map.put("UserPartnerPreferences", userPartnerPreferences.get());
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("Error", "UserPartnerPreferences not found with ID: " + userPartnerPreferencesId);
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			log.error("Exception in GetUserPartnerPreferences By Id: " + e.getMessage(), e);
			throw new GenericException("Error while fetching user user partner preferences by ID");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateUserPartnerPreferences(String mobileNumber, UserPartnerPreferences userPartnerPreferences) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserRegistrationProfile existingProfile = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
			if (existingProfile == null) {
				throw new GenericException("Profile with mobile number " + mobileNumber + " not found.");
			}
			UserPartnerPreferences existingDetails =  existingProfile.getUserPartnerPreferences();
			if (existingDetails != null) {

				existingDetails.setPreferredLocation(userPartnerPreferences.getPreferredLocation());
				existingDetails.setAnyOtherPreferences(userPartnerPreferences.getAnyOtherPreferences());
				existingDetails.setDesiredJobValue(userPartnerPreferences.getDesiredJobValue());
				existingDetails.setFamilyStatus(userPartnerPreferences.getFamilyStatus());
				existingDetails.setFamilyValue(userPartnerPreferences.getFamilyValue());

				UserPartnerPreferences savedDetails = userPartnerPreferencesRepo.save(existingDetails);
				map.put("UserPartnerPreferences", savedDetails);
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("Error", "UserPartnerPreferences not found with mobileNumber: " + mobileNumber);
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			log.error("Exception in UserPartnerPreferences: " + e.getMessage(), e);
			throw new GenericException("Error while updating User Partner Preferences");
		}
		return map;
	}

}
