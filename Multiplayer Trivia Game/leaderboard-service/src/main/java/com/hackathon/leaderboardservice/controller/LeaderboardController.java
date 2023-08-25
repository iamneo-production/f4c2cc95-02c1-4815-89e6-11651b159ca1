package com.hackathon.leaderboardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
	@GetMapping()
    public String home(){
        return "Home leaderboardservice";
    }
}
