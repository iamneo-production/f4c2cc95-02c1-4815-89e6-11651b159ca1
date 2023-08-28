package com.hackathon.gameplaymechanicsservice.repository;

import com.hackathon.gameplaymechanicsservice.entity.SinglePlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SinglePlayerEntityRepo extends JpaRepository<SinglePlayerEntity,Integer> {

    public List<SinglePlayerEntity> findByUserID(int userID);

    public SinglePlayerEntity findByGameID(int gameId);

    @Query(value = "SELECT  start_time FROM single_player_entity " +
            "WHERE gameid = :gameId AND userid = :playerId", nativeQuery = true)
    public Date findScoreAndTimeByGameIdAndPlayerId(int gameId, int playerId);

//    @Query(value = "UPDATE score_player_entity SET final_score = :finalScore " +
//            "WHERE gameid = :gameId AND userid = :playerId", nativeQuery = true)
//    public void updateFinalScore(int gameId, int playerId, int finalScore);
}
