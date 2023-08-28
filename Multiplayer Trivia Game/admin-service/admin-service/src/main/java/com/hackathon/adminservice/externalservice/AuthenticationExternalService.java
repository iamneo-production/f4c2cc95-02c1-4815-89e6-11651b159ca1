package com.hackathon.adminservice.externalservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("authentication-service")
public interface AuthenticationExternalService {

	@GetMapping("/authenticate/getRole")
	public String getRole(@RequestHeader(name = "Authorization") String tokenDup);
}
