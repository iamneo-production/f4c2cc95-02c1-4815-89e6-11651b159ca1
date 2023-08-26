package com.hackathon.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.authenticationservice.exception.UserNotValidException;
import com.hackathon.authenticationservice.external.RegisterationService;
import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;
import com.hackathon.authenticationservice.model.UserDetails;
import com.hackathon.authenticationservice.utils.JwtUtil;

@Service
public class AuthenticationService {

	@Autowired
	RegisterationService registerationService;
	@Autowired
	JwtUtil jwtUtil;
	
	public String login(LoginRequest user){
		String token = null;
		Role role=null;
		role= registerationService.loginUser(user);
		if(role!=null) {
			
			token=jwtUtil.generateToken(new UserDetails(user.getUserName(), user.getPassword(), role));
		}
		else
			throw new UserNotValidException();
		return token;
	}
	
	
 }
