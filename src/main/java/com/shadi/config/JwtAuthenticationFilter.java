package com.shadi.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shadi.exception.CustomException;
import com.shadi.profile.entity.UserRegistrationProfile;
import com.shadi.service.UserService;
import com.shadi.utils.AppConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {



	@Autowired
	private UserService userService;
	@Autowired
	private JwtHelpers jwtHelpers;
//	private List<String> skipUrls = Arrays.asList("/api/v1/auth/**","/api/v1/user/**","/api/v1/admin/**","/api/**");
	private List<String> skipUrls = Arrays.asList("/api/**");
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return this.skipUrls.stream().anyMatch(url->new AntPathRequestMatcher(url).matches(request)) ;
	}

	

	@SuppressWarnings("null")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String mobileNumber = null;
		String jwtToken = null;
		log.info("token:- " + requestTokenHeader);
	    final ObjectMapper mapper = new ObjectMapper();	
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		final Map<String, Object> errorMap = new HashMap<>();


		try {
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				mobileNumber = this.jwtHelpers.getUsernameFromToken(jwtToken);
				System.out.println("mob " +mobileNumber);
				if (mobileNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserRegistrationProfile profile = this.userService.loadUserByUsername(mobileNumber);
					System.out.println("profile " +profile);

					if (this.jwtHelpers.validateToken(jwtToken, profile)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								profile, null, profile.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					} else {
						log.info("authentication failed");
					}
				}

				else {
					log.warn("JWT Token does not begin with Bearer String");
				}

			}else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				
				System.out.println("un auth---------------");
				CustomException authorisedExe = new CustomException(AppConstants.Unauthorized, AppConstants.Unauthorized_desc,
						LocalDateTime.now(), "Token is null",request.getServletPath());
						errorMap.put(AppConstants.statusCode, authorisedExe.getStatusCode());
						errorMap.put(AppConstants.status, authorisedExe.getStatus());
						errorMap.put(AppConstants.timeStamp, authorisedExe.getTimestamp().toString());
						errorMap.put(AppConstants.statusMessage, authorisedExe.getMessage());
						errorMap.put(AppConstants.description, request.getServletPath());
						mapper.writeValue(response.getOutputStream(), errorMap);
			}

		}

		catch (Exception e) {
		CustomException authorisedExe = new CustomException(AppConstants.Unauthorized, AppConstants.Unauthorized_desc,
		LocalDateTime.now(), e.getMessage(),request.getServletPath());
		errorMap.put(AppConstants.statusCode, authorisedExe.getStatusCode());
		errorMap.put(AppConstants.status, authorisedExe.getStatus());
		errorMap.put(AppConstants.timeStamp, authorisedExe.getTimestamp().toString());
		errorMap.put(AppConstants.statusMessage, authorisedExe.getMessage());
		errorMap.put(AppConstants.description, request.getServletPath());
		mapper.writeValue(response.getOutputStream(), errorMap);

		}

		filterChain.doFilter(request, response);

	}

}
