package com.olive.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

@Component
@Data
public class JwtUtils {
   @Value("${app.secret}")
	private String secret;
   
   //1.generate Token
	public String generateToken(String username) {
		return Jwts.builder()
				.setId("12345")
				.setSubject(username)
				.setIssuer("Olive Crypto System")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+
						TimeUnit.MINUTES.toMillis(10)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();
	}
	
	//2.read the token
	 public  Claims readToken(String token) {
		  return Jwts.parser()
				 .setSigningKey(secret.getBytes())
				 .parseClaimsJws(token)
				 .getBody();
	 }
	 
	//3. read the username
	public String getUsername(String token) {
		return readToken(token).getSubject();
	}
	
}
