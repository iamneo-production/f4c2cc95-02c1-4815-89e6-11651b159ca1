package com.hackathon.adminservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;
import com.hackathon.adminservice.repo.AdminRepository;
import com.hackathon.adminservice.service.AdminService;

@Service
public class AdminSeviceImpl implements AdminService{
	
	@Autowired
	AdminRepository adminrepo;

	@Override
	public String addQuestion(Question question) {
		adminrepo.save(question);
		return "Question added to Data Base";
	}
	
	@Override
	public List<Question1> getListOfQuestions(String category, String level,int limit)
	{
		List<Question1> questions=adminrepo.getListOfQuestions(category,level,limit);
		return questions;
	}

	@Override
	public String getQuestionAnswer(int qid) {
		Question obj=adminrepo.findById(qid).get();
		return obj.getCorrectAnswer();
	}
}
