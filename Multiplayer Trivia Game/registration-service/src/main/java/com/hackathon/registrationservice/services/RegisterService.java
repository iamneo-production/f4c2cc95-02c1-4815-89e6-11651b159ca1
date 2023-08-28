package com.hackathon.registrationservice.services;

import java.util.List;

import com.hackathon.registrationservice.entity.LoginRequest;
import com.hackathon.registrationservice.entity.User;





public interface RegisterService {

	User saveUser(User user);
	
	List<User> getAllUsers();
	
	boolean getUser(String mail);
	
	User userLogin(LoginRequest userCredentials);
	
}
