package com.hackathon.adminservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;

public interface AdminRepository extends JpaRepository<Question,Integer> {
	
	@Query(value = "SELECT question_description,opt1,opt2,opt3,opt4 FROM Question " +
			"WHERE category = :category AND level = :level limit :limit", nativeQuery = true)
	public List<Question1> getListOfQuestions(String category, String level,int limit);
}
