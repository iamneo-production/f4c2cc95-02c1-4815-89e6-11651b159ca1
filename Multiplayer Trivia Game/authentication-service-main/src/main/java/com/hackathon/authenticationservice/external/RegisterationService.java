package com.hackathon.authenticationservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;


@FeignClient("REGISTERATION-SERVICE")
public interface RegisterationService {
	
	@PostMapping("/registration/login")
	public Role loginUser(LoginRequest loginRequest);

}
