package com.hackathon.authenticationservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;
import com.hackathon.authenticationservice.model.UserDetails;

@FeignClient("REGISTRATION-SERVICE")
public interface RegisterationService {
	
	@PostMapping("/regiseration/login")
	public Role loginUser(LoginRequest loginRequest);

}
