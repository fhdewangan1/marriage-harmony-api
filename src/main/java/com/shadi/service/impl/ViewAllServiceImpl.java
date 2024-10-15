package com.shadi.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shadi.exception.GenericException;
import com.shadi.exception.InternalServerError;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.records.AllUserRecord;
import com.shadi.records.ProfileRecords;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.ViewAllService;
import com.shadi.utils.AppConstants;
import com.shadi.utils.JavaMailUtil;
import com.shadi.utils.RandomPasswordGenerator;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ViewAllServiceImpl implements ViewAllService {

	private static final Logger logger = LoggerFactory.getLogger(ViewAllServiceImpl.class);

	@Autowired
	private UserProfileRegistrationRepo userProfileRegistrationRepo;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private JavaMailUtil javaMailUtil;

	@Autowired
	private RandomPasswordGenerator randomPasswordGenerator;

	public Map<String, Object> viewAll(String gender, int page, int size, String sortBy) {
		Map<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
			Page<UserRegistrationProfile> profilePage;

			profilePage = userProfileRegistrationRepo.findAll(pageable);
			if (gender == null || gender.trim().isEmpty()) {
				// Fetch all profiles if gender is null
			} else {
				// Fetch profiles based on opposite gender
				String oppositeGender = getOppositeGender(gender);
				profilePage = userProfileRegistrationRepo.findAllByGender(oppositeGender, pageable);
			}

			List<ProfileRecords> profiles = profilePage.getContent().stream()
					.map(user -> new ProfileRecords(user.getMobileNumber(), user.getFirstName(), user.getLastName(),
							user.getAge(), user.getGender(),
							Optional.ofNullable(user.getLangKnown()).map(langList -> String.join(", ", langList))
									.orElse(""),
							user.getReligion(), user.getCommunity(), user.getDob(), user.getResidence()))
					.collect(Collectors.toList());

			map.put("result", profiles);
			map.put("currentPage", profilePage.getNumber());
			map.put("totalItems", profilePage.getTotalElements());
			map.put("totalPages", profilePage.getTotalPages());
		} catch (Exception e) {
			logger.error("Error occurred while fetching profiles", e); // Log the exception
			map.put("result", "error");
			map.put("message", e.getMessage()); // Include the error message in the response
		}
		return map;
	}

	private String getOppositeGender(String gender) {
		if (AppConstants.MALE.equalsIgnoreCase(gender)) {
			return AppConstants.FEMALE;
		} else if (AppConstants.FEMALE.equalsIgnoreCase(gender)) {
			return AppConstants.MALE;
		} else {
			throw new IllegalArgumentException("Invalid gender value: " + gender);
		}
	}

	@Override
	public byte[] viewProfileImage(String mobileNumber) {
		try {
			UserRegistrationProfile profile = userProfileRegistrationRepo.findByMobileNumber(mobileNumber);
			if (profile != null) {
				return profile.getProfileImage(); // Assuming this method exists in UserRegistrationProfile
			} else {
				throw new Exception("Profile not found for mobile number: " + mobileNumber);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error loading profile image", e);
		}
	}

	@Override
	public Map<String, Object> getAllUserDetails(String mobNum) {
		Map<String, Object> userMap = new HashMap<>();
		try {
			if (mobNum != null) {
				UserRegistrationProfile allRecords = this.userProfileRegistrationRepo.findByMobileNumber(mobNum);
				log.info("allRecords {} " + allRecords);

				String languages = Optional.ofNullable(allRecords.getLangKnown())
						.filter(langList -> langList != null && !langList.isEmpty()) // Check if the list is not empty
						.map(langList -> String.join(", ", langList)).orElse("NA"); // Default message when empty

				AllUserRecord allUserRecord = new AllUserRecord(allRecords.getMobileNumber(), allRecords.getFirstName(),
						allRecords.getLastName(), allRecords.getAge(), allRecords.getGender(), languages,
						allRecords.getReligion(), allRecords.getCommunity(), allRecords.getDob(),
						allRecords.getResidence(),allRecords.getUserMailId(), allRecords.getUserFamilyDetails(),
						allRecords.getUserLifeStyleAndEducation(), allRecords.getUserPersonalDetails(),
						allRecords.getUserPartnerPreferences());
				userMap.put("response", allUserRecord);
			} else {
				userMap.put("response", "Mobile number is Empty");
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			throw new GenericException("Something went wrong while fetching All User Details");
		}
		return userMap;
	}

	@Override
	@Transactional
	public Map<Object, Object> resetPassword(String mobileNumber, String dateOfBirth) {
		var userDetails = this.userProfileRegistrationRepo.findByMobileNumber(mobileNumber);

		if (userDetails != null && userDetails.getMobileNumber().equalsIgnoreCase(mobileNumber)
				&& userDetails.getDob().isEqual(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
			var newPassword = this.randomPasswordGenerator.doGeneratePassword(11);
			Map<Object, Object> mailMap = null;
			try {
				mailMap = this.javaMailUtil.sendTextMail(userDetails.getUserMailId(), "Login Credentials",
						userDetails.getFirstName() + " " + userDetails.getLastName(), newPassword);
				this.userProfileRegistrationRepo.updateFinoUserPassword(this.passwordEncoder.encode(newPassword),
						mobileNumber);
			} catch (Exception e) {
				throw new InternalServerError(e.getMessage());
			}

			return mailMap;
		} else {
			throw new GenericException("Please check your userName or date of birth and try again...");
		}
	}
}