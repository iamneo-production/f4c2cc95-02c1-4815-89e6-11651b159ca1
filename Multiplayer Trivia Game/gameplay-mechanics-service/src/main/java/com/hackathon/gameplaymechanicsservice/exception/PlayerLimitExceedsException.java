package com.hackathon.gameplaymechanicsservice.exception;

public class PlayerLimitExceedsException extends RuntimeException{

    public PlayerLimitExceedsException(String msg)
    {
        super(msg);
    }
}
