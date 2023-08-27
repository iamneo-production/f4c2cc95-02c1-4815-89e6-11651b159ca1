package com.hackathon.leaderboardservice.model;

import java.util.List;

public class PlayersHistory {
	List<SinglePlayerScore> SinglePlayGame;
	List<RoomScore> RoomPlayGame;
	
	public PlayersHistory() {}
	public PlayersHistory(List<SinglePlayerScore> singlePlayGame, List<RoomScore> roomPlayGame) {
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
	public List<RoomScore> getRoomPlayGame() {
		return RoomPlayGame;
	}
	public void setRoomPlayGame(List<RoomScore> roomPlayGame) {
		RoomPlayGame = roomPlayGame;
	}
	
}
