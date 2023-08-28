
package com.hackathon.gameplaymechanicsservice.service;

import com.hackathon.gameplaymechanicsservice.entity.RoomStatus;
import com.hackathon.gameplaymechanicsservice.entity.RoomsEntity;
import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
import com.hackathon.gameplaymechanicsservice.entity.SinglePlayerEntity;
import com.hackathon.gameplaymechanicsservice.exception.InvalidRoomIDException;
import com.hackathon.gameplaymechanicsservice.exception.SubmissionException;
import com.hackathon.gameplaymechanicsservice.repository.RoomsEntityRepo;
import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
import com.hackathon.gameplaymechanicsservice.feignclient.QuestionFeignClient;
import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
import com.hackathon.gameplaymechanicsservice.response.PlayerAnswerResponse;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreEntityRepo scoreEntityRepo;

    @Autowired
    private RoomsEntityRepo roomsEntityRepo;

    @Autowired
    private QuestionFeignClient questionFeignClient;

    @Autowired
    private SinglePlayerEntityRepo singlePlayerEntityRepo;



    private static final double WEIGHT_POINTS = 0.7;
    private static final double WEIGHT_TIME = 0.3;

    @Autowired
    private FeignService feignService;
    String roomIDL=null;
    public String submitAnswers(String roomId,LinkedList<PlayerAnswerResponse> playerAnswerResponses,String token)
    {
        RoomsEntity roomsEntity =  roomsEntityRepo.findById(roomId).get();

        if (roomsEntity.getRoomID()==null)
            throw new InvalidRoomIDException("you have entered room id : "+ roomId+" is invalid ,please enter valid roomId");
        else {
            //get userID by using feign client
            int userId =  feignService.getUserIDFromToken(token);

            List<ScoresEntity> scoresEntityTemp = scoreEntityRepo.findByRoomID(roomId);
            for (ScoresEntity sc : scoresEntityTemp) {
                if (sc.getParticipantID() == userId && sc.getRoomID().equals(roomId) && sc.getEndTime()!= null) {
                    throw new SubmissionException("you have already submitted the quiz ");
                }
            }

            List<ScoresEntity> scoresEntity = scoreEntityRepo.findByRoomID(roomId);
            scoresEntity.forEach(sc -> {
                if (sc.getParticipantID() == userId) {
                    sc.setEndTime(new Date());
                    scoreEntityRepo.save(sc);
                }
            });


            int score = 0;
            int count = 0;

            LinkedList<Integer> qNumbers = new LinkedList<>();
            for (PlayerAnswerResponse obj : playerAnswerResponses) {
                qNumbers.add(obj.getQuestionID());
            }

            HashMap<Integer, String> qAnswers = questionFeignClient.getQuestionAnswer(qNumbers);
            for (PlayerAnswerResponse obj : playerAnswerResponses) {
                if (obj.getCorrectOption().equals(qAnswers.get(obj.getQuestionID()))) {
                    score++;
                    count++;
                }
            }

            Date times = scoreEntityRepo.findScoreAndTimeByRoomIdAndPlayerId(roomId, userId);


            long timeInMillis1 = times.getTime();
             long timeInMillis2 = new Date().getTime();

            long millisecondsDifference = Math.abs(timeInMillis1 - timeInMillis2);

            int secondsDifference = (int) millisecondsDifference / 1000;
            double finalScore = Math.abs(WEIGHT_POINTS * (double) score) - ((WEIGHT_TIME/600) * (double) secondsDifference);

            List<ScoresEntity> scoresEntityMain = scoreEntityRepo.findByRoomID(roomId);
            for (ScoresEntity sc : scoresEntityMain) {
                 if (sc.getParticipantID() == userId) {
                    sc.setFinalScore((int) finalScore*10);
                    sc.setNoOfCorrectAnswers(count);
                    scoreEntityRepo.save(sc);
                }
            }



            roomsEntityRepo.findById(roomId).ifPresent(room -> {
                room.setRoomStatus(RoomStatus.COMPLETED.toString());
            });

            return "your score is : " + (int) finalScore;
        }

    }

    public String submitAnswers(int gameId, LinkedList<PlayerAnswerResponse> playerAnswerResponses,String token)
    {
        SinglePlayerEntity singlePlayerEntity = singlePlayerEntityRepo.findByGameID(gameId);
        if ( singlePlayerEntity.getgameID() < 1 )
            throw new InvalidRoomIDException("you have entered game id : "+singlePlayerEntity.getgameID()+" is invalid ,please enter valid gameId");
        else {

            if (singlePlayerEntity.getEndTime() !=null )
                throw new SubmissionException("you have already submitted quiz");

            SinglePlayerEntity singleScoresEntity = singlePlayerEntityRepo.findByGameID(gameId);
            singleScoresEntity.setEndTime(new Date());
            singlePlayerEntityRepo.save(singleScoresEntity);

            int score = 0;
            int count = 0;

            LinkedList<Integer> qNumbers = new LinkedList<>();
            for (PlayerAnswerResponse obj : playerAnswerResponses) {
                qNumbers.add(obj.getQuestionID());
            }

            HashMap<Integer, String> qAnswers = questionFeignClient.getQuestionAnswer(qNumbers);
            for (PlayerAnswerResponse obj : playerAnswerResponses) {
                if (obj.getCorrectOption().equals(qAnswers.get(obj.getQuestionID()))) {
                    score++;
                    count++;
                }
            }
            int userId =  feignService.getUserIDFromToken(token);

            Date times = singlePlayerEntityRepo.findScoreAndTimeByGameIdAndPlayerId(gameId, userId);
            //System.out.println("size:"+times.size());


            long timeInMillis1 = times.getTime();
            long timeInMillis2 = new Date().getTime();
            long millisecondsDifference = Math.abs(timeInMillis1 - timeInMillis2);


            int secondsDifference = (int) millisecondsDifference / 1000;
            double finalScore = Math.abs(WEIGHT_POINTS * (double) score) - ((WEIGHT_TIME/60) * (double) secondsDifference);


            SinglePlayerEntity singleScoresEntityS = singlePlayerEntityRepo.findByGameID(gameId);
            singleScoresEntity.setScore((int) finalScore*10);
            singleScoresEntity.setNoOfQuestions(count);
            singlePlayerEntityRepo.save(singleScoresEntity);

            return "your score is : " + (int) finalScore;
        }
    }
}

