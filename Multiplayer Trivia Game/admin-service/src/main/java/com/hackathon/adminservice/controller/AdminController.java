package com.hackathon.adminservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;
import com.hackathon.adminservice.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminservice;
	
	@PostMapping("/questions")
	public String addQuestion(@RequestBody Question question)
	{
		return adminservice.addQuestion(question);
	}
	@GetMapping("/questions")
	public List<Question1> getListOfQuestions(@RequestParam("category") String category,@RequestParam("level") String level,@RequestParam("limit") int limit)
	{
		return adminservice.getListOfQuestions(category,level,limit);
	}
	@GetMapping("/getAnswerByQid")
	public String getQuestionAnswer(@RequestParam("qid") int qid)
	{
		return adminservice.getQuestionAnswer(qid);
	}
}
