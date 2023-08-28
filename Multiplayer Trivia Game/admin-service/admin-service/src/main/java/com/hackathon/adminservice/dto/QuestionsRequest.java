package com.hackathon.adminservice.dto;

public class QuestionsRequest {
	public QuestionsRequest() {
		super();
	}

	public QuestionsRequest(String category, String level, int noOFQuestions) {
		super();
		this.category = category;
		this.level = level;
		this.noOFQuestions = noOFQuestions;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getNoOFQuestions() {
		return noOFQuestions;
	}

	public void setNoOFQuestions(int noOFQuestions) {
		this.noOFQuestions = noOFQuestions;
	}

	private String category;
	private String level;
	private int noOFQuestions;
}