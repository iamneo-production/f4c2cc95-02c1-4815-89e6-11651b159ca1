package com.hackathon.gameplaymechanicsservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value ="AUTHENTICATION-SERVICE", path = "/authenticate")
public interface AutencticationfeignClient {

    @GetMapping("/getUserMail")
    public String getUserMail(@RequestHeader(name = "Authorization" ) String tokenDup) ;
}
