package com.hackathon.gameplaymechanicsservice.service;

 import com.hackathon.gameplaymechanicsservice.entity.RoomStatus;
 import com.hackathon.gameplaymechanicsservice.entity.ScoresEntity;
 import com.hackathon.gameplaymechanicsservice.repository.RoomsEntityRepo;
 import com.hackathon.gameplaymechanicsservice.repository.ScoreEntityRepo;
 import com.hackathon.gameplaymechanicsservice.feignclient.QuestionFeignClient;
 import com.hackathon.gameplaymechanicsservice.repository.SinglePlayerEntityRepo;
 import com.hackathon.gameplaymechanicsservice.response.PlayerAnswerResponse;
 import org.bouncycastle.util.Integers;
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

    private SinglePlayerEntityRepo singlePlayerEntityRepo;


    int userId;
    private static final double WEIGHT_POINTS = 0.7;
    private static final double WEIGHT_TIME = 0.3;
    public String submitAnswers(String roomId,LinkedList<PlayerAnswerResponse> playerAnswerResponses)
    {
        //get userID by using feign client
      //  ScoresEntity scoresEntity = scoreEntityRepo.fi

//        playerAnswerResponses.forEach( ans ->
//        {
//            //create a feign client to get answer of the question
//            String correctAnswer =questionFeignClient.getQuestionAnswer(ans.getQuestionID());
//            if (ans.getCorrectOption().equals(correctAnswer)) score++;
//
//        });

        int score =0;

        LinkedList<Integer> qNumbers = new LinkedList<>();
        for(PlayerAnswerResponse obj : playerAnswerResponses)
        {
            qNumbers.add(obj.getQuestionID());
        }

        HashMap<Integer,String> qAnswers = questionFeignClient.getQuestionAnswer(qNumbers);
        for(PlayerAnswerResponse obj : playerAnswerResponses)
        {
            if(obj.getCorrectOption().equals(qAnswers.get(obj.getQuestionID()))) score++;
        }

         List<Date> times = scoreEntityRepo.findScoreAndTimeByRoomIdAndPlayerId(roomId,userId);
         Date startTime = times.get(0);
         Date endTime = times.get(1);

        long timeInMillis1 = startTime.getTime();
        long timeInMillis2 = endTime.getTime();
        long millisecondsDifference = Math.abs(timeInMillis2 - timeInMillis1);

//        java.util.Date utilDate1 = new java.util.Date(startTime.getTime());
//        java.util.Date utilDate2 = new java.util.Date(endTime.getTime());
//
//        // Calculate the time difference in milliseconds
//        long millisecondsDifference = utilDate2.getTime() - utilDate1.getTime();

        // Convert milliseconds to seconds
        int secondsDifference =(int) millisecondsDifference / 1000;
        double finalScore = (WEIGHT_POINTS * (double) score) - (WEIGHT_TIME * (double) secondsDifference);

        scoreEntityRepo.updateFinalScore(roomId,userId,(int)finalScore);

        roomsEntityRepo.findById(roomId).ifPresent(room -> {
            room.setRoomStatus(RoomStatus.COMPLETED.toString());
        });

        return "your score is : "+(int)finalScore;

    }

    public String submitAnswers(int gameId, LinkedList<PlayerAnswerResponse> playerAnswerResponses)
    {
        //get userID by using feign client


//        playerAnswerResponses.forEach( ans ->
//        {
//            //create a feign client to get answer of the question
//            String correctAnswer =questionFeignClient.getQuestionAnswer(ans.getQuestionID());
//            if (ans.getCorrectOption().equals(correctAnswer)) score++;
//
//        });
        int score =0;

        LinkedList<Integer> qNumbers = new LinkedList<>();
        for(PlayerAnswerResponse obj : playerAnswerResponses)
        {
            qNumbers.add(obj.getQuestionID());
        }

        HashMap<Integer,String> qAnswers = questionFeignClient.getQuestionAnswer(qNumbers);
        for(PlayerAnswerResponse obj : playerAnswerResponses)
        {
             if(obj.getCorrectOption().equals(qAnswers.get(obj.getQuestionID()))) score++;
        }
        List<Date> times = singlePlayerEntityRepo.findScoreAndTimeByGameIdAndPlayerId(gameId,userId);
        Date startTime = times.get(0);
        Date endTime = times.get(1);

        long timeInMillis1 = startTime.getTime();
        long timeInMillis2 = endTime.getTime();
        long millisecondsDifference = Math.abs(timeInMillis2 - timeInMillis1);


//        java.util.Date utilDate1 = new java.util.Date(startTime.getTime());
//        java.util.Date utilDate2 = new java.util.Date(endTime.getTime());

        // Calculate the time difference in milliseconds
        //long millisecondsDifference = utilDate2.getTime() - utilDate1.getTime();

        // Convert milliseconds to seconds
        int secondsDifference =(int) millisecondsDifference / 1000;
        double finalScore = (WEIGHT_POINTS * (double) score) - (WEIGHT_TIME * (double) secondsDifference);

        singlePlayerEntityRepo.updateFinalScore(gameId,userId,(int)finalScore);

        return "your score is : "+(int)finalScore;

    }
}

