package com.shadi.service;

import java.time.LocalDate;
import java.util.Map;

public interface ViewAllService {

	public Map<String, Object> viewAll(String gender, int page, int size, String sortBy);

	byte[] viewProfileImage(String gender);

	public Map<String, Object> getAllUserDetails(String mobNum);

	Map<Object, Object> resetPassword(String mobileNumber, String dateOfBirth);

}
