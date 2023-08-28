package com.hackathon.gameplaymechanicsservice.repository;

import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScoreEntityRepo extends JpaRepository<ScoresEntity,String> {

    public List<ScoresEntity> findByParticipantID(int userID);

    public List<ScoresEntity> findByRoomID(String roomId);


    @Query(value = "SELECT  start_time FROM scores_entity " +
                    "WHERE roomid = :roomId AND participantid = :playerId", nativeQuery = true)
     public Date findScoreAndTimeByRoomIdAndPlayerId(String roomId, int playerId);

}
