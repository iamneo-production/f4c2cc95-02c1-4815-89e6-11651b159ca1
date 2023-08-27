package com.hackathon.gameplaymechanicsservice.request;

public class QuestionsRequest {

    private String category;

    private String level;

    private int noOFQuestions;

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
}
