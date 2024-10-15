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

import com.shadi.entity.UserFamilyDetails;
import com.shadi.exception.GenericException;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.repo.UserFamilyDetailsRepo;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.UserFamilyDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserFamilyDetailsServiceImpl implements UserFamilyDetailsService {

	@Autowired
	private UserFamilyDetailsRepo userFamilyDetailsRepo;
	@Autowired
	private UserProfileRegistrationRepo userProfileRegistrationRepo;

	@Override
	public Map<String, Object> saveUserFamilylDetails(UserFamilyDetails userFamilyDetails, String mobileNumber) {
		Map<String, Object> map = new HashMap<>();
		UserRegistrationProfile findByMobileNumber = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
		try {
			if (findByMobileNumber == null) {
				throw new GenericException("Profile Not Avaliable with mobile number :" + mobileNumber);
			} else {
				userFamilyDetails.setFamilyDetails(userFamilyDetails.getFamilyDetails());
				userFamilyDetails.setFamilyStatus(userFamilyDetails.getFamilyDetails());
				userFamilyDetails.setFamilyValue(userFamilyDetails.getFamilyValue());
				userFamilyDetails.setFatherName(userFamilyDetails.getFatherName());
				userFamilyDetails.setFatherOccupation(userFamilyDetails.getFatherOccupation());
				userFamilyDetails.setMotherName(userFamilyDetails.getMotherName());
				userFamilyDetails.setMotherOccupation(userFamilyDetails.getMotherOccupation());
				userFamilyDetails.setMaternalGotra(userFamilyDetails.getMaternalGotra());
				userFamilyDetails.setNoOfBrothers(userFamilyDetails.getNoOfBrothers());
				userFamilyDetails.setNoOfBrothersMarried(userFamilyDetails.getNoOfBrothersMarried());
				userFamilyDetails.setNoOfSisters(userFamilyDetails.getNoOfSisters());
				userFamilyDetails.setNoOfSistersMarried(userFamilyDetails.getNoOfSistersMarried());
				userFamilyDetails.setNoOfFamilyMembers(userFamilyDetails.getNoOfFamilyMembers());
				userFamilyDetails.setUserRegistrationProfile(findByMobileNumber);
				UserFamilyDetails details = userFamilyDetailsRepo.save(userFamilyDetails);
				map.put("UserFamilyDetails", details);
				map.put("status", HttpStatus.OK.value());
			}
		} catch (Exception e) {
			log.error("Exception in userFamilylDetails  " + e.getMessage(), e);
			throw new GenericException("Error While Saving Details");
		}
		return map;
	}

	@Override
	public Map<String, Object> getUserFamilylDetails(int page, int size) {
		Map<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, size);

			Page<UserFamilyDetails> userFamilyDetailsPage = userFamilyDetailsRepo.findAll(pageable);

			if (userFamilyDetailsPage.getContent().isEmpty()) {
				map.put("message", "No data found");
				map.put("GetUserFamilylDetails", Collections.emptyList());
				map.put("status", HttpStatus.NOT_FOUND.value());
			} else {
				map.put("GetUserFamilylDetails", userFamilyDetailsPage.getContent());
				map.put("status", HttpStatus.OK.value());
			}
			map.put("totalPages", userFamilyDetailsPage.getTotalPages());
			map.put("totalElements", userFamilyDetailsPage.getTotalElements());
			map.put("currentPage", userFamilyDetailsPage.getNumber());
			map.put("pageSize", userFamilyDetailsPage.getSize());
		} catch (Exception e) {
			log.error("Exception in getUserFamilylDetails: " + e.getMessage(), e);
			throw new GenericException("Error while fetching user family details");
		}

		return map;
	}

	@Override
	public Map<String, Object> getUserFamilylDetailsById(Long userFamilyId) {
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<UserFamilyDetails> userFamilyDetailsOpt = userFamilyDetailsRepo.findById(userFamilyId);

			if (userFamilyDetailsOpt.isPresent()) {
				map.put("UserFamilyDetails", userFamilyDetailsOpt.get());
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("Error", "UserFamilyDetails not found with ID: " + userFamilyId);
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			log.error("Exception in getUserFamilylDetailsById: " + e.getMessage(), e);
			throw new GenericException("Error while fetching user family details by ID");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateUserFamilylDetails(String mobileNumber, UserFamilyDetails updatedDetails) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserRegistrationProfile existingProfile = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
			if (existingProfile == null) {
				throw new GenericException("Profile with mobile number " + mobileNumber + " not found.");
			}

			UserFamilyDetails existingDetails = existingProfile.getUserFamilyDetails();
			if (existingDetails != null) {

				existingDetails.setFamilyDetails(updatedDetails.getFamilyDetails());
				existingDetails.setFamilyStatus(updatedDetails.getFamilyStatus());
				existingDetails.setFamilyValue(updatedDetails.getFamilyValue());
				existingDetails.setFatherName(updatedDetails.getFatherName());
				existingDetails.setFatherOccupation(updatedDetails.getFatherOccupation());
				existingDetails.setMotherName(updatedDetails.getMotherName());
				existingDetails.setMotherOccupation(updatedDetails.getMotherOccupation());
				existingDetails.setMaternalGotra(updatedDetails.getMaternalGotra());
				existingDetails.setNoOfBrothers(updatedDetails.getNoOfBrothers());
				existingDetails.setNoOfBrothersMarried(updatedDetails.getNoOfBrothersMarried());
				existingDetails.setNoOfSisters(updatedDetails.getNoOfSisters());
				existingDetails.setNoOfSistersMarried(updatedDetails.getNoOfSistersMarried());
				existingDetails.setNoOfFamilyMembers(updatedDetails.getNoOfFamilyMembers());

				UserFamilyDetails savedDetails = userFamilyDetailsRepo.save(existingDetails);
				map.put("UserFamilyDetails", savedDetails);
				map.put("status", HttpStatus.OK.value());
			} else {
				map.put("Error", "UserFamilyDetails not found with mobileNumber: " + mobileNumber);
				map.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception e) {
			log.error("Exception in updateUserFamilyDetails: " + e.getMessage(), e);
			throw new GenericException("Error while updating family details");
		}
		return map;
	}

}
