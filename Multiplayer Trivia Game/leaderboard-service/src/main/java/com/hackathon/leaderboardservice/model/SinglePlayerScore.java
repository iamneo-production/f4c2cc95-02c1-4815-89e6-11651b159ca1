package com.hackathon.leaderboardservice.model;

import java.util.Date;

public class SinglePlayerScore {
	private String roomID;
	private int participantID;
	private int noOfCorrectAnswers;
	private Date startTime;
	private Date endTime;
	private int finalScore;
	
	public SinglePlayerScore() {}
	public SinglePlayerScore(String roomID, int participantID, int noOfCorrectAnswers, Date startTime, Date endTime,
			int finalScore) {
		super();
		this.roomID = roomID;
		this.participantID = participantID;
		this.noOfCorrectAnswers = noOfCorrectAnswers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finalScore = finalScore;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public int getParticipantID() {
		return participantID;
	}
	public void setParticipantID(int participantID) {
		this.participantID = participantID;
	}
	public int getNoOfCorrectAnswers() {
		return noOfCorrectAnswers;
	}
	public void setNoOfCorrectAnswers(int noOfCorrectAnswers) {
		this.noOfCorrectAnswers = noOfCorrectAnswers;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	
}
