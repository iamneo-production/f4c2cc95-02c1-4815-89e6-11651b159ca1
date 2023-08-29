package com.hackathon.leaderboardservice.model;



import java.util.Date;


public class SinglePlayerEntity implements Comparable<SinglePlayerEntity>{


    private int gameID;
    private int userID;

    private String cateogry;
    private String level;
    private int noOfQuestions;

    private Date startTime;
    private Date endTime;

    private int score;

    public int getgameID() {
        return gameID;
    }

    public void setgameID(int gameID) {
        this.gameID = gameID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCateogry() {
        return cateogry;
    }

    public void setCateogry(String cateogry) {
        this.cateogry = cateogry;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SinglePlayerEntity{" +
                "gameID=" + gameID +
                ", userID=" + userID +
                ", cateogry='" + cateogry + '\'' +
                ", level='" + level + '\'' +
                ", noOfQuestions=" + noOfQuestions +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", score=" + score +
                '}';
    }
    public SinglePlayerEntity() {}
    public SinglePlayerEntity(int gameID, int userID, String cateogry, String level, int noOfQuestions, Date startTime,
			Date endTime, int score) {
		super();
		this.gameID = gameID;
		this.userID = userID;
		this.cateogry = cateogry;
		this.level = level;
		this.noOfQuestions = noOfQuestions;
		this.startTime = startTime;
		this.endTime = endTime;
		this.score = score;
	}

	@Override
    public int compareTo(SinglePlayerEntity other) {
        return Integer.compare(other.getScore(), this.score);
    }

}
