package com.shadi.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	
		final ObjectMapper mapper = new ObjectMapper();	
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		final Map<String, Object> body = new HashMap<>();

//		CustomException authorisedExe = new CustomException(AppConstants.Unauthorized, AppConstants.Unauthorized_desc,
//		LocalDateTime.now(), authException.getMessage(),request.getServletPath());
//		body.put(AppConstants.statusCode, authorisedExe.getStatusCode());
//		body.put(AppConstants.status, authorisedExe.getStatus());
//		body.put(AppConstants.timeStamp, authorisedExe.getTimestamp().toString());
//		body.put(AppConstants.statusMessage, authorisedExe.getMessage());
//		body.put(AppConstants.description, request.getServletPath());
		
		
		mapper.writeValue(response.getOutputStream(), body);
	}

}
