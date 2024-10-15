package com.shadi.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shadi.exception.CustomException;
import com.shadi.utils.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		final ObjectMapper mapper = new ObjectMapper();
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			
			Map<Object, Object> map1=new HashMap<>();
			CustomException authorisedExe = new CustomException(AppConstants.Unauthorized, AppConstants.Unauthorized_desc,
					LocalDateTime.now(),"Error",request.getServletPath());
			map1.put(AppConstants.statusCode, authorisedExe.getStatusCode());
			map1.put(AppConstants.status, authorisedExe.getStatus());
			map1.put(AppConstants.timeStamp, authorisedExe.getTimestamp());
			map1.put(AppConstants.statusMessage, authorisedExe.getMessage());
			map1.put(AppConstants.description, authorisedExe.getDescription());
			 JavaTimeModule javaTimeModule = new JavaTimeModule();
			 mapper.registerModule(javaTimeModule);
			 mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);   
			mapper.writeValue(response.getOutputStream(), map1);
	}

}
