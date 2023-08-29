package com.hackathon.leaderboardservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hackathon.leaderboardservice.model.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.hackathon.leaderboardservice.exception.NoGamePlayedException;
import com.hackathon.leaderboardservice.external.GameplayMechanicsService;

@Service
public class LeaderboardService {

	@Autowired
	GameplayMechanicsService gameplayMechanicsService;

	Logger log = LoggerFactory.getLogger(LeaderboardService.class);

	@CircuitBreaker(name = "gameplayMechanicsServiceBreaker", fallbackMethod = "getPlayerHistoryFallback")
    public ResponseEntity<PlayersHistory> getPlayerHistory(int playerId) {
            List<SinglePlayerScore> singlePlayerScores = gameplayMechanicsService.getSinglePlayerScore(playerId);

			System.out.println(singlePlayerScores.size()+"rrrrrrrr");
            List<ScoresEntity> roomScores = gameplayMechanicsService.getRoomScores(playerId);
            log.info("a");
            if (singlePlayerScores.isEmpty() && roomScores.isEmpty()) {
            	log.error("Username has not played any game");
            	 throw new NoGamePlayedException();
            }

            return ResponseEntity.ok(new PlayersHistory(singlePlayerScores, roomScores));

    }

    public ResponseEntity<PlayersHistory> getPlayerHistoryFallback(int playerId, Exception ex) {
        // Log the error message
        log.error("An error occurred while fetching player history: " + ex.getMessage());
        SinglePlayerScore s1= new SinglePlayerScore("GAMEID",0,0,null,null,0);
        ScoresEntity r1= new ScoresEntity(0, "ROOMID", 0, 0,null,null, 0);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new PlayersHistory(List.of(s1), List.of(r1)));
    }

	public List<ScoresEntity> getRoomHistory(String roomId) {
		List<ScoresEntity> roomScores= gameplayMechanicsService.getRoomScoresHistory(roomId);
		if(roomScores.isEmpty())
			throw new NoGamePlayedException();

		Collections.sort(roomScores);
		return roomScores;
	}

	public List<SinglePlayerEntity> getTopScores(String category,String level)
	{
		System.out.println("enteredddddddddddddddddddddddd");

		List<SinglePlayerEntity> roomScores= gameplayMechanicsService.getAllsingle();

		System.out.println("rom size" + roomScores.size());

		List<SinglePlayerEntity> topScores=new ArrayList<>();
		for(SinglePlayerEntity obj:roomScores)
		{
			if(obj.getCateogry().equals(category) && obj.getLevel().equals(level))
			{
				topScores.add(obj);
			}
		}
		Collections.sort(topScores);
		return topScores;
	}
}
