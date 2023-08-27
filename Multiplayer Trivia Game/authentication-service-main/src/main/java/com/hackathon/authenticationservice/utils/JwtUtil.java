package com.hackathon.authenticationservice.utils;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hackathon.authenticationservice.exception.JWTNotValidException;
import com.hackathon.authenticationservice.model.Role;
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
		Claims claims = Jwts.claims().setIssuer(user.getUserName()).setIssuedAt(date).setExpiration(expiry);
		claims.put("name", user.getUserName());
		claims.put("role",user.getRole());
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
			e.printStackTrace();
			throw new JWTNotValidException();
		}
	}
	
	 
    public String getNameFromToken(String token) {
        Claims claims = null;
        try {
             claims = Jwts.parser()
                     .setSigningKey(secret)
                     .parseClaimsJws(token)
                     .getBody();
        }
        catch(Exception e){
			e.printStackTrace();
			throw new JWTNotValidException();
        }

        return (String) claims.get("name");
    }
 // method for get role of user from token
	public String getRoleFromToken(String token) {
		Claims claims = null;
        try {
             claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch(Exception e){
			e.printStackTrace();
			throw new JWTNotValidException();
        }

        return (String) claims.get("role");
	}

}
