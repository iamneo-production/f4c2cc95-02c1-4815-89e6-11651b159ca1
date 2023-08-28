package com.hackathon.adminservice.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Category;
import com.hackathon.adminservice.entity.Level;
import com.hackathon.adminservice.entity.Question;
import com.hackathon.adminservice.exception.InvalidCategoryException;
import com.hackathon.adminservice.exception.InvalidLevelException;
import com.hackathon.adminservice.exception.LimitIsGreaterThanQuestionAvailabilityException;
import com.hackathon.adminservice.exception.QuestionIdNotValidException;
import com.hackathon.adminservice.repo.AdminRepository;
import com.hackathon.adminservice.service.AdminService;

@Service
public class AdminSeviceImpl implements AdminService {

	@Autowired
	AdminRepository adminrepo;

	@Override
	public ResponseEntity<Question> addQuestion(Question question) {
		adminrepo.save(question);
		return ResponseEntity.ok(question);
	}

	@Override
	public List<Question1> getListOfQuestions(String category, String level, int limit) {
		try {
			Category.valueOf(category);
		} catch (IllegalArgumentException e) {
			throw new InvalidCategoryException();
		}
		try {
			Level.valueOf(level);
		} catch (IllegalArgumentException e) {
			throw new InvalidLevelException();
		}

		if (limit <= getNumberOfQuestionsAvailable(category, level))
			return adminrepo.getListOfQuestions(category, level, limit);
		else {
			throw new LimitIsGreaterThanQuestionAvailabilityException(getNumberOfQuestionsAvailable(category, level));
		}
	}

	@Override
	public Map<Integer, String> getQuestionAnswer(List<Integer> qNo) {

		HashMap<Integer, String> qAnswers = new HashMap<>();

		for (Integer q : qNo) {
			Optional<Question> obj = adminrepo.findById(q);
			if (obj.isPresent())
				qAnswers.put(q, obj.get().getCorrectAnswer());
			else
				throw new QuestionIdNotValidException();
		}
		return qAnswers;
	}

	@Override
	public int getNumberOfQuestionsAvailable(String category, String level) {
		return adminrepo.getNumberOfQuestionsAvailable(category, level);
	}
}
