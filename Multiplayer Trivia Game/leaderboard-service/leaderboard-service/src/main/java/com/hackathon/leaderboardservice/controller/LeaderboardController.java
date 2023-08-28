package com.hackathon.leaderboardservice.controller;

import java.util.List;

import com.hackathon.leaderboardservice.model.ScoresEntity;
import com.hackathon.leaderboardservice.model.SinglePlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hackathon.leaderboardservice.model.RoomScore;
import com.hackathon.leaderboardservice.service.LeaderboardService;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
	
	@Autowired
	LeaderboardService leaderboardService;
	
	@GetMapping()
    public String home(){
        return "Home leaderboardservice";
    }
	
	@GetMapping("/history")
	public ResponseEntity<?> getPlayerHistory(@RequestParam("playerId") Integer playerId){
		return leaderboardService.getPlayerHistory(playerId);
	}
	
	@GetMapping("/history/{roomId}")
	public ResponseEntity<List<ScoresEntity>> getRoomHistory(@PathVariable("roomId") String roomId){
		return new ResponseEntity<List<ScoresEntity>>(leaderboardService.getRoomHistory(roomId), HttpStatus.OK);
	}
	
	@GetMapping("/getTopScores/{category}/{level}")
	public List<SinglePlayerEntity> getTopScores(@PathVariable("category") String category, @PathVariable("level") String level)
	{
		return leaderboardService.getTopScores(category, level);
	}
	
	
	
}
