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
public class UserPartnerPreferences {

	@TableGenerator(allocationSize = 10, initialValue = 0, name = "user_partner_sequence")
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_partner_sequence")
	private long userPartnerPreferencesId;
	private String familyStatus;
	private String familyValue;
	private String preferredLocation;
	private String desiredJobValue;
	private String anyOtherPreferences;
	@OneToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private UserRegistrationProfile userRegistrationProfile;
}
