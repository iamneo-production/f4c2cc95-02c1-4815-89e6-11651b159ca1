package com.hackathon.gameplaymechanicsservice.service;

import com.hackathon.gameplaymechanicsservice.entity.*;
import com.hackathon.gameplaymechanicsservice.exception.InvalidRoomIDException;
import com.hackathon.gameplaymechanicsservice.exception.NotRoomOwnerException;
import com.hackathon.gameplaymechanicsservice.exception.PlayerLimitExceedsException;
import com.hackathon.gameplaymechanicsservice.feignclient.AutencticationfeignClient;
import com.hackathon.gameplaymechanicsservice.feignclient.QuestionFeignClient;
import com.hackathon.gameplaymechanicsservice.repository.RoomsEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
 import com.hackathon.gameplaymechanicsservice.request.QuestionsRequest;
import com.hackathon.gameplaymechanicsservice.response.Question1;
import com.hackathon.gameplaymechanicsservice.response.QuestonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {


    @Autowired
    private RoomsEntityRepo roomsEntityRepo;

    @Autowired
    private ScoreEntityRepo scoreEntityRepo;

    @Autowired
    private QuestionFeignClient questionFeignClient;

    @Autowired
    private FeignService feignService;

    @Autowired
    private AutencticationfeignClient autencticationfeignClient;

    public ResponseEntity<String> createRoom(RoomsEntity roomsEntity,String token)
    {
        System.out.println("-----------entered");
        UUID uid = UUID.randomUUID();
        roomsEntity.setRoomID(uid.toString());

        //create fiegn client to get user id
         int userId =  feignService.getUserIDFromToken(token);
         roomsEntity.setRoomOwnerID(userId);

        roomsEntity.setRoomStatus(RoomStatus.CREATED.toString());

        ArrayList<Integer> players = new ArrayList<>();
        players.add(userId);
        roomsEntity.setParticipants(players);

        roomsEntityRepo.save(roomsEntity);

        String responseMsg = "your room has created with the ID : "+roomsEntity.getRoomID()+" with " +
                ""+roomsEntity.getNoOfQuestions()+" Questions. now you can invite your friends with the room code to join the game.";

        return ResponseEntity.ok(responseMsg);

    }

    String roomID = null;

    public String joinRoom( String roomId,String token)
    {

        roomsEntityRepo.findById(roomId).ifPresent( (room) ->
        {
            roomID = room.getRoomID();
            int userId =  feignService.getUserIDFromToken(token);

            ArrayList<Integer> players = room.getParticipants();
             if(players.size()< room.getNoOfParticipants())
            {            //create fiegn client to get user id
                players.add(userId);
                room.setParticipants(players);
                roomsEntityRepo.save(room);
            }
            else
                throw new PlayerLimitExceedsException("the required number of player in the have already joined this room. you are not allowed to join this room");
        });

        if (roomId==null)
            throw new InvalidRoomIDException("you have entered room id : "+roomId+" is invalid ,please enter valid roomId");
         else {
            roomID=null;
            return "you have join in the room. the room owner will start the game...";

        }

    }

    public String checkJoinParticipants(String roomId,String token)
    {
        int userId =  feignService.getUserIDFromToken(token);

        RoomsEntity roomsEntity =  roomsEntityRepo.findById(roomId).get();
        if(roomsEntity!=null) {
            // create a fiegn client to get userid
            if (roomsEntity.getRoomOwnerID() == userId) {
                if (roomsEntity.getParticipants().size() != roomsEntity.getNoOfParticipants())
                    return "all the participants haven't joined yet please wait until  the join of all participants ";
                else return "all participants has joined. you can start the game ";
            } else {
                roomID = null;
                throw new NotRoomOwnerException("your are not the owner of room id : " + roomId);
            }
        }
        else
            throw new InvalidRoomIDException("you have entered room id : "+roomId+" is invalid ,please enter valid roomId");
    }


    private static HashMap<String,List<Question1>> roomIDQuestions = new HashMap<>();
    public ResponseEntity<?> startGame(String roomId,String token)
    {
        RoomsEntity roomsEntity =  roomsEntityRepo.findById(roomId).get();
        // create a feign client to get the user id
        int userId =  feignService.getUserIDFromToken(token);
        if(roomsEntity.getRoomStatus().equals(RoomStatus.STARTED.toString()))
            throw new InvalidRoomIDException("the game has alredy start you can't start again");


            if (roomsEntity.getRoomOwnerID() == userId) {
                if (roomId == roomsEntity.getRoomID()) {
                    roomsEntity.setRoomStatus(RoomStatus.STARTED.toString());
                    roomsEntityRepo.save(roomsEntity);

                    Date date = new Date();

                    ArrayList<Integer> players = roomsEntity.getParticipants();
                    players.forEach((player) -> {
                        ScoresEntity scoresEntity = new ScoresEntity();
                        scoresEntity.setRoomID(roomId);
                        scoresEntity.setParticipantID(player);
                        scoresEntity.setNoOfCorrectAnswers(0);
                        scoresEntity.setStartTime(date);
                        scoresEntity.setEndTime(null);
                        scoreEntityRepo.save(scoresEntity);
                    });

                    QuestionsRequest questionsRequest = new QuestionsRequest();
                    questionsRequest.setCategory(roomsEntity.getCategory());
                    questionsRequest.setLevel(roomsEntity.getLevel());
                    questionsRequest.setNoOFQuestions(roomsEntity.getNoOfQuestions());


                    List<Question1> questions = questionFeignClient.getListOfQuestions(
                            questionsRequest.getCategory(),
                            questionsRequest.getLevel(),
                            questionsRequest.getNoOFQuestions()
                    );

                    roomIDQuestions.put(roomId, questions);
                    Collections.shuffle(questions);
                    return ResponseEntity.ok(questions);

                } else
                    throw new InvalidRoomIDException("you have entered room id : " + roomId + " is invalid ,please enter valid roomId");

            } else {
                roomID = null;
                throw new NotRoomOwnerException("your are not the owner of room id : " + roomId);

            }


    }

    public List<Question1> getRoomIDQuestions(String roomId,String token)
    {

        List<Question1> quest = roomIDQuestions.get(roomId);
        if(quest== null)
        {
             throw new InvalidRoomIDException("you have entered room id : "+roomId+" is invalid ,please enter valid roomId");
        }
        else
            Collections.shuffle(quest);
        return quest;


    }

    @Autowired
    private SinglePlayerEntityRepo singlePlayerEntityRepo;

    public  QuestonResponse startGame(QuestionsRequest questionsRequest, String token) {

        //get user id from feign client


        int userId =  feignService.getUserIDFromToken(token);

        SinglePlayerEntity singlePlayerEntity = new SinglePlayerEntity();
        singlePlayerEntity.setUserID(userId);
        singlePlayerEntity.setCateogry(questionsRequest.getCategory());
        singlePlayerEntity.setLevel(questionsRequest.getLevel());
        singlePlayerEntity.setNoOfQuestions(questionsRequest.getNoOFQuestions());
        singlePlayerEntity.setStartTime(new Date());

         SinglePlayerEntity singlePlayer = singlePlayerEntityRepo.save(singlePlayerEntity);




        List<Question1> questions = questionFeignClient.getListOfQuestions(
                questionsRequest.getCategory(),
                questionsRequest.getLevel(),
                questionsRequest.getNoOFQuestions()
        );

        Collections.shuffle(questions);

        String str = "your game id is :"+singlePlayer.getgameID();
        QuestonResponse response = new QuestonResponse();
        response.setQue(questions);
        response.setGameIDReponse(str);
        return response;


    }
}
