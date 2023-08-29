package com.hackathon.gameplaymechanicsservice.controller;

import com.hackathon.gameplaymechanicsservice.entity.RoomsEntity;
import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
import com.hackathon.gameplaymechanicsservice.entity.SinglePlayerEntity;
 import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
import com.hackathon.gameplaymechanicsservice.request.QuestionsRequest;
 import com.hackathon.gameplaymechanicsservice.response.PlayerAnswerResponse;
import com.hackathon.gameplaymechanicsservice.response.Question1;
import com.hackathon.gameplaymechanicsservice.service.FeignService;
import com.hackathon.gameplaymechanicsservice.service.RoomService;
import com.hackathon.gameplaymechanicsservice.service.ScoreService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;


 import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/quizAPI")
public class GamePlayController {


    @Autowired
    private RoomService roomService;

    @Autowired
    private ScoreService scoreService;
    
    @Autowired 
	Environment env;
	// http://localhost:8181/quizAPI/loadBalancedDemo run 2 instances
	@GetMapping("/loadBalancedDemo")
	public String loadBalancedDemo() {
		return "Gameplay-Mechanics-Service running on port : "+env.getProperty("local.server.port", Integer.class);
		}

    @PostMapping("/createRoom")
    private ResponseEntity<String> createRoom(@RequestBody RoomsEntity roomsEntity,@RequestHeader(name = "Authorization") String tokenDup) {

       return roomService.createRoom(roomsEntity,tokenDup);
    }

    @GetMapping("/joinRoom/{roomID}")
    public String joinRoom(@PathVariable("roomID") String roomId,@RequestHeader(name = "Authorization") String tokenDup) {
        return roomService.joinRoom(roomId,tokenDup);
    }

    @GetMapping("/checkParticipants/{roomID}")
    public String checkJoinParticipants(@PathVariable("roomID") String roomId,@RequestHeader(name = "Authorization") String tokenDup)
    {
        return roomService.checkJoinParticipants(roomId,tokenDup);
    }


    @GetMapping("/start/{roomID}")
   // @CircuitBreaker(name = "gameplayMechanicsServiceBreaker",fallbackMethod ="getClientFallBack" )
    public ResponseEntity<?> startGame(@PathVariable("roomID") String roomId,@RequestHeader(name = "Authorization") String tokenDup)
    {
        return roomService.startGame(roomId,tokenDup);
    }


    @GetMapping("/getRoomQuestions/{roomId}")
    public List<Question1> getRoomQuestions(@PathVariable("roomId") String roomId,@RequestHeader(name = "Authorization") String tokenDup)
    {
        return roomService.getRoomIDQuestions(roomId,tokenDup);

    }

    @PostMapping("/submit/{roomId}")
    public String submitAnswers(@PathVariable("roomId") String roomId,@RequestBody LinkedList<PlayerAnswerResponse> playerAnswerResponses,@RequestHeader(name = "Authorization") String tokenDup)
    {
        return scoreService.submitAnswers(roomId,playerAnswerResponses,tokenDup);
    }

    @PostMapping("/startSinglePlayerGame")
    public List<Question1> startGame(@RequestBody QuestionsRequest questionsRequest,@RequestHeader(name = "Authorization") String tokenDup) {
        return roomService.startGame(questionsRequest,tokenDup);
    }

    @PostMapping("submitAnswers/{gameId}")
    public String submitAnswers(@PathVariable("gameId") int gameId, @RequestBody LinkedList<PlayerAnswerResponse> playerAnswerResponses,@RequestHeader(name = "Authorization") String tokenDup)
    {
        return scoreService.submitAnswers(gameId,playerAnswerResponses,tokenDup);
    }

        @Autowired
    private FeignService feignService;

    @GetMapping("/getAllSingleScores/{playerId}")
    public List<SinglePlayerEntity> getUserAllSingleScoresByID(@PathVariable int playerId)
    {
       return feignService.getUserAllSingleScoresByID(playerId);
    }

    @GetMapping("/getUserAllRoomScore/{playerId}")
    public List<ScoresEntity> getUserAllRoomScoresByUserID(@PathVariable int playerId)
    {
        return feignService.getUserAllRoomScoresByUserID(playerId);
    }

    @GetMapping("/getRoomScore/{roomID}")
    public List<ScoresEntity> getRoomScoresByID(@PathVariable("roomID") String roomID) {

        return feignService.getRoomScoresByID(roomID);
    }

    @Autowired
    private ScoreEntityRepo scoreEntityRepo;
    @Autowired
    private SinglePlayerEntityRepo singlePlayerEntityRepo;

    @GetMapping("/getallData")
    public List<ScoresEntity> getAll()
    {
        return scoreEntityRepo.findAll();
    }

    @GetMapping("/quizAPI/getallSingleData")
    public List<SinglePlayerEntity> getAllsingle(){
        return singlePlayerEntityRepo.findAll();
    }

}
