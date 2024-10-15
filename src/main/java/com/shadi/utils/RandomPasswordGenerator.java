package com.shadi.utils;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class RandomPasswordGenerator {

	public String doGeneratePassword(int passwordLength) {
		SecureRandom secureRandom = new SecureRandom();
		String randomChaString = "123456789@$#^&*()!~ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String password = "";
		for (int i = 0; i < passwordLength; i++) {
			password = password + randomChaString.charAt(secureRandom.nextInt(randomChaString.length()));
		}
		return password;
	}

}