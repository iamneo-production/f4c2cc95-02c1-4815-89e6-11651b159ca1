package com.hackathon.adminservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class AdminController {
	@GetMapping()
    public String home(){
        return "Home adminservice";
    }
}
