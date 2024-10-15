package com.shadi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shadi.profile.entity.UserRegistrationProfile;

public interface UserService extends UserDetailsService{

	UserRegistrationProfile loadUserByUsername(String username);
	
}
