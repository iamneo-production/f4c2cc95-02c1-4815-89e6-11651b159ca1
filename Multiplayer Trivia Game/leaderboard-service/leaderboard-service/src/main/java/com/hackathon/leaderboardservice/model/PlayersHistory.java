package com.hackathon.leaderboardservice.model;

import java.util.List;

public class PlayersHistory {
	List<SinglePlayerScore> SinglePlayGame;
	List<ScoresEntity> RoomPlayGame;
	
	public PlayersHistory() {}
	public PlayersHistory(List<SinglePlayerScore> singlePlayGame, List<ScoresEntity> roomPlayGame) {
		super();
		SinglePlayGame = singlePlayGame;
		RoomPlayGame = roomPlayGame;
	}
	public List<SinglePlayerScore> getSinglePlayGame() {
		return SinglePlayGame;
	}
	public void setSinglePlayGame(List<SinglePlayerScore> singlePlayGame) {
		SinglePlayGame = singlePlayGame;
	}
	public List<ScoresEntity> getRoomPlayGame() {
		return RoomPlayGame;
	}
	public void setRoomPlayGame(List<ScoresEntity> roomPlayGame) {
		RoomPlayGame = roomPlayGame;
	}
	
}
