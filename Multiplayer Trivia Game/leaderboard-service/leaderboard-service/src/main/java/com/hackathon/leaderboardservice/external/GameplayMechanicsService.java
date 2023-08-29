package com.hackathon.leaderboardservice.external;

import java.util.List;

import com.hackathon.leaderboardservice.model.ScoresEntity;
import com.hackathon.leaderboardservice.model.SinglePlayerEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hackathon.leaderboardservice.model.SinglePlayerScore;

@FeignClient("GAMEPLAY-MECHANICS-SERVICE")
public interface GameplayMechanicsService {

	@GetMapping("/quizAPI/getAllSingleScores/{playerId}")
	List<SinglePlayerScore> getSinglePlayerScore(@PathVariable int playerId);

	@GetMapping("/quizAPI/getUserAllRoomScore/{playerId}")
	List<ScoresEntity> getRoomScores(@PathVariable int playerId);

	@GetMapping("/quizAPI/getRoomScore/{roomId}")
	List<ScoresEntity> getRoomScoresHistory(@PathVariable String roomId);
	
	@GetMapping("/quizAPI/getallData")
	public List<ScoresEntity> getAll();

	@GetMapping("/quizAPI/getallSingleData")
	public List<SinglePlayerEntity> getAllsingle();

}
