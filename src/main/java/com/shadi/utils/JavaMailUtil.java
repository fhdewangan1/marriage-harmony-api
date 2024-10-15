package com.shadi.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shadi.exception.GenericException;

import jakarta.mail.internet.MimeMessage;

@Component
public class JavaMailUtil {


	@Value("${spring.mail.username}")
	private String setFrom;

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private TemplateEngine templateEngine;

	public Map<Object, Object> sendTextMail(String setTo, String mailSubject,String userName,String password) {
		MimeMessage message = emailSender.createMimeMessage();
		Map<Object, Object> mailMap = new HashMap<>();
		final Context thymContext = new Context();
		thymContext.setVariable("userName", userName);
		thymContext.setVariable("password",password);
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
			mimeMessageHelper.setFrom(this.setFrom);
			mimeMessageHelper.setTo(setTo);
			mimeMessageHelper.setCc(this.setFrom);
			mimeMessageHelper.setSubject(mailSubject);
			mimeMessageHelper.setText(this.templateEngine.process("passwordSender.html", thymContext), true);
			emailSender.send(message);
			mailMap.put(AppConstants.statusCode, AppConstants.ok);
			mailMap.put(AppConstants.status, AppConstants.success);
			mailMap.put(AppConstants.statusMessage, AppConstants.passwordSentSuccessfully);

		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericException(e.getMessage());
		}
		return mailMap;
	}


}
