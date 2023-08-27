package com.hackathon.leaderboardservice.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hackathon.leaderboardservice.model.RoomScore;
import com.hackathon.leaderboardservice.model.SinglePlayerScore;

@FeignClient("GAMEPLAY-MECHANICS-SERVICE")
public interface GameplayMechanicsService {

	@GetMapping("/quizAPI/getAllSingleScores/{playerId}")
	List<SinglePlayerScore> getSinglePlayerScore(@PathVariable int playerId);

	@GetMapping("/quizAPI/getUserAllRoomScore/{playerId}")
	List<RoomScore> getRoomScores(@PathVariable int playerId);

	@GetMapping("/quizAPI/getRoomScore/{roomId}")
	List<RoomScore> getRoomScoresHistory(@PathVariable String roomId);

}
