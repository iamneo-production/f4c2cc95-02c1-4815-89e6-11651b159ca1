package com.hackathon.adminservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;

public interface AdminService {

	public ResponseEntity<Question> addQuestion(Question question);

	public List<Question1> getListOfQuestions(String category, String level, int limit);

	public int getNumberOfQuestionsAvailable(String category, String level);

	Map<Integer, String> getQuestionAnswer(List<Integer> qNo);
}
