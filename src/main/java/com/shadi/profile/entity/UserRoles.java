package com.shadi.profile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long rId;
	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private UserRegistrationProfile userRegistrationProfile;
	
}
