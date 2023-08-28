package com.hackathon.gameplaymechanicsservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value ="REGISTRATION-SERVICE", path = "/registration")
public interface RegisterFeignClient {

    @GetMapping("/getUserID/{email}")
    public int getUserIdByEmail(@PathVariable("email") String email);

}
