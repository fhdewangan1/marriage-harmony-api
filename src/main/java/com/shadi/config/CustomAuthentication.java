package com.shadi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shadi.exception.AccessDeniedException;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.service.UserService;




@Component
public class CustomAuthentication implements AuthenticationProvider {

	private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		String mobileNumber = (String)authentication.getName();
		String password = (String) authentication.getCredentials();
		 UserRegistrationProfile registrationProfile = this.userService.loadUserByUsername(mobileNumber);
		if (registrationProfile == null || !registrationProfile.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
			throw new AccessDeniedException("Please check your mobile number");
		}

		else if (!this.passwordEncoder.matches(password, registrationProfile.getPassword())) {
			throw new AccessDeniedException("Incorrect Password, Please try again!!");
		}
		
//		else if (!registrationProfile.isEnabled()) {
//			throw new AccessDeniedException("sorry your account is inactive..........");
//		}

		java.util.Collection<? extends GrantedAuthority> authorities = registrationProfile.getAuthorities();

		return new UsernamePasswordAuthenticationToken(registrationProfile,password, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {

		return true;
	}

}
