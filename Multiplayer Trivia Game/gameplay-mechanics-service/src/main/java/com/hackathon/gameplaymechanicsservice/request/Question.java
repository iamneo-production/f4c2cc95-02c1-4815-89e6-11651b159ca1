package com.hackathon.gameplaymechanicsservice.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;




public class Question {



    @Override
    public String toString() {
        return "Question [id=" + id + ", category=" + category + ", level=" + level + ", question=" + questionDescription
                + ", opt1=" + opt1 + ", opt2=" + opt2 + ", opt3=" + opt3 + ", opt4=" + opt4 + ", correctAnswer="
                + correctAnswer + "]";
    }



    public Question(String category, String level, String question, String opt1, String opt2, String opt3, String opt4,
                    String correctAnswer) {
        super();
        this.category = category;
        this.level = level;
        this.questionDescription = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.correctAnswer = correctAnswer;
    }



    public Question() {
        super();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getQuestion() {
        return questionDescription;
    }
    public void setQuestion(String question) {
        this.questionDescription = question;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getOpt1() {
        return opt1;
    }
    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }
    public String getOpt2() {
        return opt2;
    }
    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }
    public String getOpt3() {
        return opt3;
    }
    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }
    public String getOpt4() {
        return opt4;
    }
    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    @Id
    private Integer id;
    private String category;
    private String level;
    private String questionDescription;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    private String correctAnswer;







}
