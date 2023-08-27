package com.hackathon.gameplaymechanicsservice.service;

import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
import com.hackathon.gameplaymechanicsservice.entity.SinglePlayerEntity;
import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeignService {

    int userID;

    @Autowired
    private SinglePlayerEntityRepo singlePlayerEntityRepo;

    @Autowired
    private ScoreEntityRepo scoreEntityRepo;
    public List<SinglePlayerEntity> getUserAllSingleScoresByID(int userID)
    {
        //create feign client to get userID
        List<SinglePlayerEntity> scoreList =null;
        scoreList = singlePlayerEntityRepo.findByUserID(userID);
        if(scoreList == null) {
            scoreList = new ArrayList<>();
            return scoreList;
        }
        return scoreList;

    }

    public List<ScoresEntity> getUserAllRoomScoresByUserID(int userID)
    {
        //create feign client to get userID
        List<ScoresEntity> scoreList =null;
        scoreList = scoreEntityRepo.findByParticipantID(userID);
        if(scoreList == null) {
            scoreList = new ArrayList<>();
            return scoreList;
        }
        return scoreList;

    }

    public List<ScoresEntity> getRoomScoresByID(String roomID)
    {
        //create feign client to get userID
        List<ScoresEntity> scoreList =null;
        scoreList = scoreEntityRepo.findByRoomID(roomID);
        if(scoreList == null) {
            scoreList = new ArrayList<>();
            return scoreList;
        }
        return scoreList;

    }


}
