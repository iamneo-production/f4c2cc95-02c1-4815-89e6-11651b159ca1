package com.hackathon.gameplaymechanicsservice.feignclient;

 import com.hackathon.gameplaymechanicsservice.response.Question1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@FeignClient(value ="ADMIN-SERVICE", path = "/admin")
public interface QuestionFeignClient {

    @GetMapping("/questions")
    public List<Question1> getListOfQuestions(@RequestParam("category") String category, @RequestParam("level") String level, @RequestParam("limit") int limit);


    @PostMapping("/getAnswerByQid")
    public HashMap<Integer, String> getQuestionAnswer(@RequestBody LinkedList<Integer> questionNos);




}
