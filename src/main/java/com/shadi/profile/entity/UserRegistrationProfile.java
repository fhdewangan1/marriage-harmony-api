package com.shadi.profile.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shadi.entity.UserFamilyDetails;
import com.shadi.entity.UserLifeStyleAndEducation;
import com.shadi.entity.UserPartnerPreferences;
import com.shadi.entity.UserPersonalDetails;
import com.shadi.utils.StringListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRegistrationProfile implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique = true)
	private String mobileNumber;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String userMailId;
	@Convert(converter = StringListConverter.class)
	@Column(columnDefinition = "LONGTEXT")
	private List<String> langKnown; // Store as List<String>
	@Column(nullable = false)
	private String password;
	private String religion;
	@Column(nullable = false)
	private String confirmPassword;
	private String community;
	private LocalDate dob;
	private String residence;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String extension;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] profileImage;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userRegistrationProfile", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<UserRoles> userRole;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userRegistrationProfile")
	@JsonManagedReference
	private UserFamilyDetails userFamilyDetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userRegistrationProfile")
	@JsonManagedReference
	private UserLifeStyleAndEducation userLifeStyleAndEducation;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userRegistrationProfile")
	@JsonManagedReference
	private UserPersonalDetails userPersonalDetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userRegistrationProfile")
	@JsonManagedReference
	private UserPartnerPreferences userPartnerPreferences;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.userRole.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList();

	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.mobileNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}

	

}
