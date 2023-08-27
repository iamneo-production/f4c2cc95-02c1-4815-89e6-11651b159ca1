package com.hackathon.authenticationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.authenticationservice.exception.RegisterationServiceException;
import com.hackathon.authenticationservice.exception.UserNotValidException;
import com.hackathon.authenticationservice.external.RegisterationService;
import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;
import com.hackathon.authenticationservice.model.UserDetails;
import com.hackathon.authenticationservice.utils.JwtUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class AuthenticationService {

	@Autowired
	RegisterationService registerationService;
	@Autowired
	JwtUtil jwtUtil;
	
	Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	
	@CircuitBreaker(name="registerationServiceBreaker",fallbackMethod = "loginUserfalllback")
	public String login(LoginRequest user){
		String token = null;
		Role role=null;
		log.info("a");
		role= registerationService.loginUser(user);
		log.info("b");
		if(role!=null) {
			
			token=jwtUtil.generateToken(new UserDetails(user.getUserName(), user.getPassword(), role));
		}
		else
			throw new UserNotValidException();
		return token;
	}
	
	public String loginUserfalllback(LoginRequest user,Exception ex) {
		log.error(ex.getMessage());
		log.error(ex.getClass().toString());
		throw new RegisterationServiceException(ex.getMessage());
	}
	
	
 }
