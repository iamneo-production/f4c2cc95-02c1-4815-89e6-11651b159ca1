package com.hackathon.gameplaymechanicsservice.exception;

public class InvalidRoomIDException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public InvalidRoomIDException(String msg)
    {
        super(msg);
    }
}
