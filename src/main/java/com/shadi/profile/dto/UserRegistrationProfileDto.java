package com.shadi.profile.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationProfileDto {
	private String mobileNumber;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	List<String> langKnown;
	private String password;
	private String religion;
	private String confirmPassword;
	private String community;
	private LocalDate dob;
	private String residence;
	private Date createdTime;
	private Date updatedTime;
	private MultipartFile profileImage;
	private String userRole;
	private String maildId;

}
