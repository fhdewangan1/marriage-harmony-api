package com.shadi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.repo.UserProfileRegistrationRepo;
import com.shadi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserProfileRegistrationRepo repo;

	@Override
	public UserRegistrationProfile loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
		return this.repo.findByMobileNumber(mobileNumber);
	}

}
