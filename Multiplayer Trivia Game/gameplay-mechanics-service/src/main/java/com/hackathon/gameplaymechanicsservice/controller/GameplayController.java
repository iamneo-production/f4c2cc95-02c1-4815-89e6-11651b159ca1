package com.hackathon.gameplaymechanicsservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quize")
public class GameplayController {
	@GetMapping()
    public String home(){
        return "Home gameplaymechanicsservice";
    }
}
