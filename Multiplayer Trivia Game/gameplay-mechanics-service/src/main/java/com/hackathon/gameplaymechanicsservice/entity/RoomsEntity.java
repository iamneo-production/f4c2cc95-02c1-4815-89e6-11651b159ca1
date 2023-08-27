package com.hackathon.gameplaymechanicsservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

@Entity
public class RoomsEntity {

    @Id
    private String roomID;

    private ArrayList<Integer> participants;

    private int roomOwnerID;

    private String roomStatus;

    private String category;

    private String level;

    private int noOfQuestions;
    private int noOfParticipants;

    public int getNoOfParticipants() {
        return noOfParticipants;
    }

    public void setNoOfParticipants(int noOfParticipants) {
        this.noOfParticipants = noOfParticipants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }


    public ArrayList<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Integer> participants) {
        this.participants = participants;
    }

    public int getRoomOwnerID() {
        return roomOwnerID;
    }

    public void setRoomOwnerID(int roomOwnerID) {
        this.roomOwnerID = roomOwnerID;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "RoomsEntity{" +
                "roomID='" + roomID + '\'' +
                ", participants=" + participants +
                ", roomOwnerID=" + roomOwnerID +
                ", roomStatus='" + roomStatus + '\'' +
                ", category='" + category + '\'' +
                ", level='" + level + '\'' +
                ", noOfQuestions=" + noOfQuestions +
                '}';
    }
}
