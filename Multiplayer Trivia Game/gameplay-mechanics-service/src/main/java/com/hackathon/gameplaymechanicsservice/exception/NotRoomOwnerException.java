package com.hackathon.gameplaymechanicsservice.exception;

public class NotRoomOwnerException extends RuntimeException{

    public NotRoomOwnerException(String msg)
    {
        super(msg);
    }
}
