package com.hackathon.gameplaymechanicsservice.entity;

 import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.Id;

 import java.util.Date;

 @Entity
public class ScoresEntity {

     public int getSerialNo() {
         return serialNo;
     }

     public void setSerialNo(int serialNo) {
         this.serialNo = serialNo;
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

     @Override
     public String toString() {
         return "ScoresEntity{" +
                 "serialNo=" + serialNo +
                 ", roomID='" + roomID + '\'' +
                 ", participantID=" + participantID +
                 ", noOfCorrectAnswers=" + noOfCorrectAnswers +
                 ", startTime=" + startTime +
                 ", endTime=" + endTime +
                 ", finalScore=" + finalScore +
                 '}';
     }

     @Id
     @GeneratedValue
     private int serialNo;

    private String roomID;
    private int participantID;

    private int noOfCorrectAnswers;

    private Date startTime;

    private Date endTime;

    private int finalScore;


}
