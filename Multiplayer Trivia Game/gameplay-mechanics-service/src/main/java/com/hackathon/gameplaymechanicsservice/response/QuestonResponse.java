package com.hackathon.gameplaymechanicsservice.response;

import java.util.List;

public class QuestonResponse {

    private String gameIDReponse;
   private List<Question1> que;


    public List<Question1> getQue() {
        return que;
    }

    public void setQue(List<Question1> que) {
        this.que = que;
    }

    public String getGameIDReponse() {
        return gameIDReponse;
    }

    public void setGameIDReponse(String gameIDReponse) {
        this.gameIDReponse = gameIDReponse;
    }
}
