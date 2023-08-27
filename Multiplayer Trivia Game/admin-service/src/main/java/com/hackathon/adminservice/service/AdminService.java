package com.hackathon.adminservice.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;

public interface AdminService {
	
	public String addQuestion(Question question);
	public List<Question1> getListOfQuestions(String category, String level,int limit);
	public String getQuestionAnswer(int qid);
}
