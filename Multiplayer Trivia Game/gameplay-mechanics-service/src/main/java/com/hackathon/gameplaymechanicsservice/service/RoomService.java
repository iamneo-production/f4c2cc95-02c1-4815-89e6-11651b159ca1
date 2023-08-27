package com.hackathon.gameplaymechanicsservice.service;

import com.hackathon.gameplaymechanicsservice.entity.*;
import com.hackathon.gameplaymechanicsservice.repository.RoomsEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
import com.hackathon.gameplaymechanicsservice.feignclient.QuestionFeignClient;
import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
 import com.hackathon.gameplaymechanicsservice.request.QuestionsRequest;
import com.hackathon.gameplaymechanicsservice.response.Question1;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private int userID;

    @Autowired
    private RoomsEntityRepo roomsEntityRepo;

    @Autowired
    private ScoreEntityRepo scoreEntityRepo;

    @Autowired
    private QuestionFeignClient questionFeignClient;

    public ResponseEntity<String> createRoom(RoomsEntity roomsEntity)
    {

        UUID uid = UUID.randomUUID();
        roomsEntity.setRoomID(uid.toString());

        //create fiegn client to get user id
        roomsEntity.setRoomOwnerID(userID);

        roomsEntity.setRoomStatus(RoomStatus.CREATED.toString());

        ArrayList<Integer> players = new ArrayList<>();
        players.add(userID);
        roomsEntity.setParticipants(players);

        roomsEntityRepo.save(roomsEntity);

        String responseMsg = "your room has created with the ID : "+roomsEntity.getRoomID()+" with " +
                ""+roomsEntity.getNoOfQuestions()+" Questions. now you can invite your friends with the room code to join the game.";

        return ResponseEntity.ok(responseMsg);

    }

    String roomID = null;
    public String joinRoom( String roomId)
    {

        roomsEntityRepo.findById(roomId).ifPresent( (room) ->
        {
            roomID = room.getRoomID();

            ArrayList<Integer> players = room.getParticipants();
            //create fiegn client to get user id
            players.add(userID);
            room.setParticipants(players);
            roomsEntityRepo.save(room);
        });

        if (roomId==null) return "you have entered room id : "+roomId+" is invalid ,please enter valid roomId";
        else {
            roomID=null;
            return "you have join in the room. the room owner will start the game...";

        }

    }

    public String checkJoinParticipants(String roomId)
    {
       RoomsEntity roomsEntity =  roomsEntityRepo.findById(roomId).get();

        // create a fiegn client to get userid
        if (roomsEntity.getRoomOwnerID() == userID)
        {
            if(roomsEntity.getParticipants().size() != roomsEntity.getNoOfParticipants())
                return "all the participants haven't joined yet please wait until  the join of all participants ";
            else  return "all participants has joined. you can start the game ";
        }
        else {
             roomID= null;
             return "your are not the owner of room id : "+roomId;
        }
    }


    public ResponseEntity<?> startGame(String roomId)
    {
        RoomsEntity roomsEntity =  roomsEntityRepo.findById(roomId).get();
        // create a feign client to get the user id
        if(roomsEntity.getRoomOwnerID() == userID)
        {
            if(roomId == roomsEntity.getRoomID()) {
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

                return ResponseEntity.ok(questions);


                //create a feign to get List of Question  from questions service
//                List<Question> questions = questionFeignClient.getQuestions(questionsRequest);
//                List<QuestionsResponse> questionsResponse = new LinkedList<>();
//                questions.forEach( que ->
//                {
//                    QuestionsResponse q = new QuestionsResponse();
//                    q.setQuestionID(que.getId());
//                    q.setQuestion(que.getQuestion());
//                    q.setOption1(que.getOpt1());
//                    q.setOption2(que.getOpt2());
//                    q.setOption3(que.getOpt3());
//                    q.setOption4(que.getOpt4());
//                    questionsResponse.add(q);
//                });
//
//                return ResponseEntity.ok(questionsResponse);
            }
            else  return  ResponseEntity.ok("you have entered room id : "+roomId+" is invalid ,please enter valid roomId");

        }
        else {
            roomID= null;
            return ResponseEntity.ok("your are not the owner of room id : "+roomId+" please wait until the owner start the game ");

        }

    }
    @Autowired
    private SinglePlayerEntityRepo singlePlayerEntityRepo;

    public List<Question1> startGame(QuestionsRequest questionsRequest) {

        //get user id from feign client

        SinglePlayerEntity singlePlayerEntity = new SinglePlayerEntity();
        singlePlayerEntity.setUserID(userID);
        singlePlayerEntity.setCateogry(questionsRequest.getCategory());
        singlePlayerEntity.setLevel(questionsRequest.getLevel());
        singlePlayerEntity.setNoOfQuestions(questionsRequest.getNoOFQuestions());
        singlePlayerEntity.setStartTime(new Date());

        singlePlayerEntityRepo.save(singlePlayerEntity);

        List<Question1> questions = questionFeignClient.getListOfQuestions(
                questionsRequest.getCategory(),
                questionsRequest.getLevel(),
                questionsRequest.getNoOFQuestions()
        );

        return questions;

//        List<Question> questions = questionFeignClient.getQuestions(questionsRequest);
//        List<QuestionsResponse> questionsResponse = new LinkedList<>();
//        questions.forEach( que ->
//        {
//            QuestionsResponse q = new QuestionsResponse();
//            q.setQuestionID(que.getId());
//            q.setQuestion(que.getQuestion());
//            q.setOption1(que.getOpt1());
//            q.setOption2(que.getOpt2());
//            q.setOption3(que.getOpt3());
//            q.setOption4(que.getOpt4());
//            questionsResponse.add(q);
//        });
//    return questionsResponse;}

    }
}
