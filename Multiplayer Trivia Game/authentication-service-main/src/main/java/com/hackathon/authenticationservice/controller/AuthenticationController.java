package com.hackathon.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.authenticationservice.exception.UserNotValidException;
import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;
import com.hackathon.authenticationservice.model.UserDetails;
import com.hackathon.authenticationservice.service.AuthenticationService;
import com.hackathon.authenticationservice.utils.JwtUtil;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
	
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public String generateToken(@RequestBody LoginRequest user )
	{
		String token = null;
		//user present in usertable return role,generate the token
		if(user.getUserName() !=null) {
			
			token = authenticationService.login(user);
		}
		else
		{
			throw new UserNotValidException();
		}
		
		//verify jwt token
		jwtUtil.verify(token);
		return token;
		
	}
	
	@GetMapping("/validate")
    public Boolean validateToken(@RequestHeader(name = "Authorization" ) String tokenDup) {
        String token= tokenDup.substring(7);
        jwtUtil.verify(token);
        return true;
    }
	
	@GetMapping("/getUserMail")
    public String getUserMail(@RequestHeader(name = "Authorization" ) String tokenDup) {
        String token= tokenDup.substring(7);
        return jwtUtil.getNameFromToken(token);
        
    }
	
	@GetMapping("/getRole")
    public String getRole(@RequestHeader(name = "Authorization" ) String tokenDup) {
        String token= tokenDup.substring(7);
        return jwtUtil.getRoleFromToken(token);
        
    }

}
