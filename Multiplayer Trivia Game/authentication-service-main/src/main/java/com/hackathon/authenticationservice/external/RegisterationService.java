package com.hackathon.authenticationservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackathon.authenticationservice.model.LoginRequest;
import com.hackathon.authenticationservice.model.Role;
<<<<<<< HEAD
import com.hackathon.authenticationservice.model.UserDetails;
=======
>>>>>>> 852b2e2919df74e3f4806812775e20b32e240c7e

@FeignClient("REGISTRATION-SERVICE")
public interface RegisterationService {
	
<<<<<<< HEAD
	@PostMapping("/regiseration/login")
=======
	@PostMapping("/registration/login")
>>>>>>> 852b2e2919df74e3f4806812775e20b32e240c7e
	public Role loginUser(LoginRequest loginRequest);

}
