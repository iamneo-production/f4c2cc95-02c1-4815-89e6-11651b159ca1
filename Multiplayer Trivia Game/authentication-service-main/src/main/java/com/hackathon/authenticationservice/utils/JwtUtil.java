package com.hackathon.authenticationservice.utils;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hackathon.authenticationservice.exception.JWTNotValidException;
import com.hackathon.authenticationservice.model.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	public static String secret = "Hacktivators";
	
	public String generateToken(UserDetails user) {
		
		long expiryDuration = 60 * 60;
		Date date = new Date();
		long milliTime = System.currentTimeMillis();
		long expiryTime = milliTime + expiryDuration * 1000;
		Date expiry = new Date(expiryTime);
		// claims
		Claims claims = Jwts.claims().setIssuer(user.getName()).setIssuedAt(date).setExpiration(expiry);
		claims.put("name", user.getName());
		// jwt with claims
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,secret).compact();
	}
	
	public void verify(String authorization)
	{
		try
		{
		Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
		}
		catch (Exception e) {
			System.out.println("exception occurrred");
			e.printStackTrace();
			throw new JWTNotValidException();
		}
	}

}
