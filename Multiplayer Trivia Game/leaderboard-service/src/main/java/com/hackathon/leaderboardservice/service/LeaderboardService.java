package com.hackathon.leaderboardservice.service;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackathon.leaderboardservice.model.ErrorModel;
import com.hackathon.leaderboardservice.model.PlayersHistory;
import com.hackathon.leaderboardservice.model.RoomScore;
import com.hackathon.leaderboardservice.model.SinglePlayerScore;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.hackathon.leaderboardservice.exception.NoGamePlayedException;
import com.hackathon.leaderboardservice.external.GameplayMechanicsService;

@Service
public class LeaderboardService {
	
	@Autowired
	GameplayMechanicsService gameplayMechanicsService;
	
	Logger log = LoggerFactory.getLogger(LeaderboardService.class);
	
	@CircuitBreaker(name = "gameplayMechanicsServiceBreaker", fallbackMethod = "getPlayerHistoryFallback")
    public ResponseEntity<?> getPlayerHistory(int playerId) {
            List<SinglePlayerScore> singlePlayerScores = gameplayMechanicsService.getSinglePlayerScore(playerId);
            List<RoomScore> roomScores = gameplayMechanicsService.getRoomScores(playerId);
            log.info("a");
            if (singlePlayerScores.isEmpty() && roomScores.isEmpty()) {
            	log.error("No HIstory");
            	 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body(new ErrorModel("ERROR501", "Username has not played any game"));
            }

            return ResponseEntity.ok(new PlayersHistory(singlePlayerScores, roomScores));
		
    }

    public ResponseEntity<?> getPlayerHistoryFallback(int playerId, Exception ex) {
        // Log the error message
        log.error("An error occurred while fetching player history: " + ex.getMessage());

        // Return an error response with HTTP status GONE (503)
        return ResponseEntity.status(HttpStatus.GONE)
                .body(new ErrorModel("ERROR503", "gameplayMechanicsService Down"));
    }

	public List<RoomScore> getRoomHistory(String roomId) {
		List<RoomScore> roomScores= gameplayMechanicsService.getRoomScoresHistory(roomId);
		if(roomScores.isEmpty())
			throw new NoGamePlayedException();
		return roomScores;
	}
}
