package com.shadi.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseStructure {
	
	private final static String statusKey="status";
	private final static String statusCodeKey="statusCode";
	private final  static String timestampKey="timestamp";
	private final  static String statusMessageKey="statusMessage";
	private  final  static String pathKey="path";
	private  final  static String responseKey="response";
	
	public static <T,U,V,W> Map<String, Object> createErrorStructure(T status,U statusCode,V statusMessage,W path,ExtraResponse<?>...extraResponses ){
		 Map<String, Object> errorMap=new HashMap<>();
		try {
			errorMap.put(statusKey, status);
			errorMap.put(statusCodeKey, statusCode);
			errorMap.put(timestampKey, getCurrentTimeStamp());
			errorMap.put(pathKey, path);
			errorMap.put(statusMessageKey, statusMessage);
			for(ExtraResponse<?> res:extraResponses) {
				if(res!=null) {
					errorMap.put(res.getResponseKey(), res.getResponseValue());
				}
			}
			
		} catch (Exception e) {
			log.info("unable construct hashmap:- {} ",e.getMessage());
		}
		return errorMap;
	}
	
	public static <T,U> Map<String, Object> createResponseStructure(T statusMessage,U response,ExtraResponse<?>...extraResponses ){
		 Map<String, Object> responseMap=new HashMap<>();
		try {
			responseMap.put(statusKey, HttpStatus.OK);
			responseMap.put(statusCodeKey, HttpStatus.OK.value());
			responseMap.put(timestampKey, getCurrentTimeStamp());
			responseMap.put(responseKey, responseKey);
			responseMap.put(statusMessageKey, statusMessage);
			for(ExtraResponse<?> res:extraResponses) {
				if(res!=null) {
					responseMap.put(res.getResponseKey(), res.getResponseValue());
				}
			}
			
		} catch (Exception e) {
			log.info("unable construct hashmap:- {} ",e.getMessage());
		}
		return responseMap;
	}
	
	
	private static String getCurrentTimeStamp() {
		var timeStamp="";
		try {
			timeStamp=ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
		} catch (Exception e) {
			log.info("unable get  timestamp:- {} ",e.getMessage());
		}
		return timeStamp;
	}

}
