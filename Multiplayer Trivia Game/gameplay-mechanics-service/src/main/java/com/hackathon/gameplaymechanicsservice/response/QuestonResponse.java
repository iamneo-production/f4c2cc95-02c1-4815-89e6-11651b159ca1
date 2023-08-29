package com.hackathon.gameplaymechanicsservice.response;

import java.util.List;

public class QuestonResponse {

   private List<Question1> que;
   private String gameIDReponse;

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
