package com.shadi.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shadi.config.JwtHelpers;
import com.shadi.exception.GenericException;
import com.shadi.exception.InternalServerError;
import com.shadi.exception.NotFoundException;
import com.shadi.profile.dto.ChangePasswordDto;
import com.shadi.profile.dto.UserRegistrationProfileDto;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.profile.entity.UserRoles;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.UserRegistrationService;
import com.shadi.utils.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private UserProfileRegistrationRepo profileRegistrationRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtHelpers jwtHelpers;

	public Map<String, Object> createProfile(UserRegistrationProfileDto dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (profileRegistrationRepo.existsById(dto.getMobileNumber())) {
				throw new GenericException("Profile with mobile number " + dto.getMobileNumber() + " already exists.");
			}

			var userRegistrationProfile = new UserRegistrationProfile();
			userRegistrationProfile.setMobileNumber(dto.getMobileNumber());
			userRegistrationProfile.setFirstName(dto.getFirstName());
			userRegistrationProfile.setLastName(dto.getLastName());
			userRegistrationProfile.setAge(dto.getAge());
			userRegistrationProfile.setGender(dto.getGender());
			userRegistrationProfile.setLangKnown(dto.getLangKnown()); // Directly use List<String>
			userRegistrationProfile.setPassword(passwordEncoder.encode(dto.getPassword()));
			userRegistrationProfile.setConfirmPassword(passwordEncoder.encode(dto.getConfirmPassword()));
			userRegistrationProfile.setCommunity(dto.getCommunity());
			userRegistrationProfile.setResidence(dto.getResidence());
			userRegistrationProfile.setReligion(dto.getReligion());
			userRegistrationProfile.setDob(dto.getDob());
			userRegistrationProfile.setUserMailId(dto.getMaildId());
			userRegistrationProfile.setCreatedTime(LocalDateTime.now());

			if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
				userRegistrationProfile.setProfileImage(dto.getProfileImage().getBytes());
				userRegistrationProfile.setExtension(dto.getProfileImage().getOriginalFilename());
			}

			var roles = new UserRoles();
			roles.setRole("USER");
			userRegistrationProfile.setUserRole(Arrays.asList(roles));
			roles.setUserRegistrationProfile(userRegistrationProfile);

			profileRegistrationRepo.save(userRegistrationProfile);
			map.put("message", "Profile created successfully");
			map.put("status", HttpStatus.OK.value());
		} catch (Exception e) {
			map.put("message", "Profile creation failed");
			System.out.println(e);
		}
		return map;
	}

	@Override
	public Map<String, Object> userLogin(String mobileNumber, String password) {

		Map<String, Object> userLoginMap = new HashMap<>();
		try {
			UserRegistrationProfile findByMobileNumber = this.profileRegistrationRepo.findByMobileNumber(mobileNumber);
			if (findByMobileNumber != null) {
				var jwtToken = this.jwtHelpers.generateToken(findByMobileNumber);
				userLoginMap.put(AppConstants.USER_NAME, findByMobileNumber.getMobileNumber());
				userLoginMap.put(AppConstants.FULL_NAME,
						findByMobileNumber.getFirstName() + " " + findByMobileNumber.getLastName());
				userLoginMap.put("gender", findByMobileNumber.getGender());
				userLoginMap.put(AppConstants.JWT_TOKEN, jwtToken);
				userLoginMap.put(AppConstants.TOKEN_EXPIRATION_IN_MILIS,
						this.jwtHelpers.getExpirationDateFromToken(jwtToken).toInstant().toEpochMilli());
				userLoginMap.put(AppConstants.USER_ROLES, findByMobileNumber.getUserRole().stream()
						.map(role -> role.getRole()).collect(Collectors.toList()));
				userLoginMap.put(AppConstants.status, AppConstants.success);
				userLoginMap.put(AppConstants.statusCode, AppConstants.ok);
				userLoginMap.put(AppConstants.statusMessage, AppConstants.userLoggedInSuccesfully);
				return userLoginMap;
			} else {
				throw new NotFoundException("Sorry No user found with the given mobile number : " + mobileNumber);
			}
		} catch (Exception e) {
			throw new InternalServerError(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> updateProfile(UserRegistrationProfileDto dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserRegistrationProfile existingProfile = profileRegistrationRepo.findByMobileNumber(dto.getMobileNumber());

			if (existingProfile == null) {
				throw new GenericException("Profile with mobile number " + dto.getMobileNumber() + " not found.");
			}

			// Update the fields that are allowed to change
			existingProfile.setFirstName(dto.getFirstName());
			existingProfile.setLastName(dto.getLastName());
			existingProfile.setAge(dto.getAge());
			existingProfile.setGender(dto.getGender());
			existingProfile.setLangKnown(dto.getLangKnown());
			existingProfile.setCommunity(dto.getCommunity());
			existingProfile.setResidence(dto.getResidence());
			existingProfile.setReligion(dto.getReligion());
			existingProfile.setDob(dto.getDob());
			existingProfile.setUpdatedTime(LocalDateTime.now());
			existingProfile.setUserMailId(dto.getMaildId());

			// Update the profile image if present
			if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
				existingProfile.setProfileImage(dto.getProfileImage().getBytes());
				existingProfile.setExtension(dto.getProfileImage().getOriginalFilename());
			}

			profileRegistrationRepo.save(existingProfile);

			map.put("message", "Profile updated successfully");
			map.put("status", HttpStatus.OK.value());
		} catch (Exception e) {
			map.put("message", "Profile update failed");
			map.put("details", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> chnagePassword(String mobileNumber, ChangePasswordDto changePasswordDto) {
		Map<String, Object> map = new HashMap<>();

		// Check if new password and confirm password match
//		if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
//			throw new GenericException("New Password and confirm password should be the same ...");
//		}

		// Find the user by mobile number
		UserRegistrationProfile isUserPresent = profileRegistrationRepo.findByMobileNumber(mobileNumber);
		if (isUserPresent == null || isUserPresent.getMobileNumber() == null) {
			throw new GenericException("Please Enter a valid User");
		}

		// Verify the old password
		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), isUserPresent.getPassword())) {
			throw new GenericException("Old password is incorrect ...");
		}

		// Update the user's password
		isUserPresent.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		profileRegistrationRepo.save(isUserPresent);

		// Prepare response
		map.put("message", "Password changed successfully");
		map.put("status", HttpStatus.OK.value());

		return map;
	}

	@Override
	public void deleteUserByMobNum(String mobileNumber) {
		Map<String, String>map = new HashMap<>();
		try {
			profileRegistrationRepo.deleteById(mobileNumber);
			map.put("message", "User Deleted Successfully");
		} catch (Exception e) {
			throw new GenericException("Error during deleting a user");
		}
		
	}
	
}
