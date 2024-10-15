package com.shadi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shadi.profile.entity.UserRegistrationProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLifeStyleAndEducation {
	@TableGenerator(allocationSize = 10, initialValue = 0, name = "user_life_details_sequence")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_life_details_sequence")
	private long userLifeStyleAndEducationId;
	private String Qualification;
	private String userOccupation;
	private String userCurrentLoc;
	private String drinking;
	private String smoking;
	private String diet;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private UserRegistrationProfile userRegistrationProfile;
}
