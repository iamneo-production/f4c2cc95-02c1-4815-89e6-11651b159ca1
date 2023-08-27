package com.hackathon.gameplaymechanicsservice.controller;

import com.hackathon.gameplaymechanicsservice.entity.RoomsEntity;
import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
import com.hackathon.gameplaymechanicsservice.entity.SinglePlayerEntity;
import com.hackathon.gameplaymechanicsservice.response.PlayerAnswerResponse;
import com.hackathon.gameplaymechanicsservice.service.FeignService;
import com.hackathon.gameplaymechanicsservice.service.RoomService;
import com.hackathon.gameplaymechanicsservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/quizAPI")
public class GamePlayController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/createRoom")
    private ResponseEntity<String> createRoom(@RequestBody RoomsEntity roomsEntity) {

       return roomService.createRoom(roomsEntity);
    }

    @GetMapping("/joinRoom/{roomID}")
    public String joinRoom(@PathVariable("roomID") String roomId) {
        return roomService.joinRoom(roomId);
    }

    @GetMapping("/checkParticipants/{roomID}")
    public String checkJoinParticipants(@PathVariable("roomID") String roomId)
    {
        return roomService.checkJoinParticipants(roomId);
    }

    @GetMapping("/start/{roomID}")
    public ResponseEntity<?> startGame(@PathVariable("roomID") String roomId)
    {
        return roomService.startGame(roomId);
    }

    @PostMapping("/submit/{roomId}")
    public String submitAnswers(@PathVariable("roomId") String roomId,@RequestBody LinkedList<PlayerAnswerResponse> playerAnswerResponses)
    {
        return scoreService.submitAnswers(roomId,playerAnswerResponses);
    }

    @Autowired
    private FeignService feignService;

    @GetMapping("/getAllSingleScores")
    public List<SinglePlayerEntity> getUserAllSingleScoresByID()
    {
       return feignService.getUserAllSingleScoresByID();
    }

    @GetMapping("/getUserAllRoomScore")
    public List<ScoresEntity> getUserAllRoomScoresByUserID()
    {
        return feignService.getUserAllRoomScoresByUserID();
    }

    @GetMapping("/getRoomScore/{roomID}")
    public List<ScoresEntity> getRoomScoresByID(@PathVariable("roomID") String roomID) {

        return feignService.getRoomScoresByID(roomID);
    }

}
