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

import com.shadi.entity.UserLifeStyleAndEducation;
import com.shadi.exception.GenericException;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.repo.UserLifeStyleAndEducationRepo;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.UserLifeStyleAndEducationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserLifeStyleAndEducationServiceImpl implements UserLifeStyleAndEducationService {
	@Autowired
	private UserLifeStyleAndEducationRepo userLifeStyleAndEducationRepo;
	@Autowired
	private UserProfileRegistrationRepo userProfileRegistrationRepo;

	@Override
	public Map<String, Object> saveUserLifeStyleAndEducationDetails(
			UserLifeStyleAndEducation userLifeStyleAndEducation,String mobileNumber) {
		Map<String, Object> map = new HashMap<>();
		UserRegistrationProfile findByMobileNumber = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
		try {
			userLifeStyleAndEducation.setDiet(userLifeStyleAndEducation.getDiet());
			userLifeStyleAndEducation.setDrinking(userLifeStyleAndEducation.getDrinking());
			userLifeStyleAndEducation.setQualification(userLifeStyleAndEducation.getQualification());
			userLifeStyleAndEducation.setSmoking(userLifeStyleAndEducation.getSmoking());
			userLifeStyleAndEducation.setUserCurrentLoc(userLifeStyleAndEducation.getUserCurrentLoc());
			userLifeStyleAndEducation.setUserOccupation(userLifeStyleAndEducation.getUserOccupation());
			userLifeStyleAndEducation.setUserRegistrationProfile(findByMobileNumber);
			UserLifeStyleAndEducation details = userLifeStyleAndEducationRepo.save(userLifeStyleAndEducation);
			map.put("userLifeStyleAndEducation", details);
			map.put("status", HttpStatus.OK.value());
		} catch (Exception e) {
			log.error("Exception in saveUserLifeStyleAndEducationDetails  " + e.getMessage(), e);
			throw new GenericException("Error While Saving Details");
		}
		return map;
	}

	@Override
	public Map<String, Object> getUserLifeStyleAndEducationDetails(int page, int size) {
		Map<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, size);

			Page<UserLifeStyleAndEducation> userPage = userLifeStyleAndEducationRepo.findAll(pageable);

			if (userPage.isEmpty()) {
				map.put("message", "No data found");
				map.put("GetUserLifeStyleAndEducationDetails", Collections.emptyList());
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("UserLifeStyleAndEducationDetails", userPage.getContent());
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
			map.put("totalPages", userPage.getTotalPages());
			map.put("totalElements", userPage.getTotalElements());
			map.put("currentPage", userPage.getNumber());
			map.put("pageSize", userPage.getSize());
		} catch (Exception e) {
			log.error("Exception in getUserLifeStyleAndEducationDetails: " + e.getMessage(), e);
			throw new GenericException("Error while fetching details");
		}
		return map;
	}

	@Override
	public Map<String, Object> getUserLifeStyleAndEducationDetailsById(Long userLifeStyleAndEducationId) {
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<UserLifeStyleAndEducation> findById = userLifeStyleAndEducationRepo
					.findById(userLifeStyleAndEducationId);
			map.put("UserLifeStyleAndEducationDetailsById", findById);
			map.put("status", HttpStatus.OK.value());
		} catch (Exception e) {
			log.error("Exception in getUserLifeStyleAndEducationDetailsById  " + e.getMessage(), e);
			throw new GenericException("Error While feting Details userLifeStyleAndEducationId");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateUserLifeStyleAndEducationDetails(String mobileNumber,
			UserLifeStyleAndEducation updatedDetails) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserRegistrationProfile existingProfile = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
			if (existingProfile == null) {
				throw new GenericException("Profile with mobile number " + mobileNumber + " not found.");
			}

			UserLifeStyleAndEducation existingDetails = existingProfile.getUserLifeStyleAndEducation();
			if (existingDetails != null) {

				existingDetails.setDiet(updatedDetails.getDiet());
				existingDetails.setDrinking(updatedDetails.getDrinking());
				existingDetails.setQualification(updatedDetails.getQualification());
				existingDetails.setSmoking(updatedDetails.getSmoking());
				existingDetails.setUserCurrentLoc(updatedDetails.getUserCurrentLoc());
				existingDetails.setUserOccupation(updatedDetails.getUserOccupation());

				UserLifeStyleAndEducation savedDetails = userLifeStyleAndEducationRepo.save(existingDetails);
				map.put("userLifeStyleAndEducation", savedDetails);
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("Error", "UserLifeStyleAndEducation not found with mobileNumber: " + mobileNumber);
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			log.error("Exception in updateUserLifeStyleAndEducationDetails: " + e.getMessage(), e);
			throw new GenericException("Error while updating lifestyle and education details");
		}
		return map;
	}
}
