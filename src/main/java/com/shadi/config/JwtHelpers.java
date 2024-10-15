package com.shadi.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.shadi.profile.entity.UserRegistrationProfile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelpers {

    private static final long JWT_TOKEN_VALIDITY=24*60*60*1000;
	
	private static final String SECRET_KEY="dd5822d4123f4a1036cf0ea5c9e0eee6987742e44ffc9f202e6147d04d5e2aef57fef161be6bf34d0c401ba5099edb91df19254a198347abfafde2a1ad44b590b0ea144f558bdd8ab1a8731e659c080a1525d11eb42b37619e53de006dd9642f9bd4e6b9b363044692a4452d8cbd36b02edc7567ed548a37b507e7df9c86e3d7";
	

	//retrieve username from jwt token
		public String getUsernameFromToken(String token) {
			return getClaimFromToken(token, Claims::getSubject);
		}
		//retrieve expiration date from jwt token
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
	    //for retrieveing any information from token we will need the secret key
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();
		}
		//check if the token has expired
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		//generate token for user
		public String generateToken(UserRegistrationProfile finoFserDetails) {
			Map<String, Object> claims = new HashMap<>();
//			claims.put("role",finoFserDetails.getAuthorities());
			return doGenerateToken(claims, finoFserDetails.getMobileNumber());
		}
		//while creating the token -
		//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
		//2. Sign the JWT using the HS512 algorithm and secret key.
		//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
		//   compaction of the JWT to a URL-safe string 
		private String doGenerateToken(Map<String, Object> claims, String subject) {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
					.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),SignatureAlgorithm.HS256).compact();
		}
		//validate token
		public Boolean validateToken(String token, UserRegistrationProfile registrationProfile) {
			final String username = getUsernameFromToken(token);
			return (username.equals(registrationProfile.getMobileNumber()) && !isTokenExpired(token));
		}
	
}
