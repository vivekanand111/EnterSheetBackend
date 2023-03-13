package com.example.demo2.exception;

public class RecordAlreadyExistsException extends RuntimeException {
    private String message;
    public RecordAlreadyExistsException(){}
    public RecordAlreadyExistsException(String message){
        super(message);
        this.message=message;
    }
}
