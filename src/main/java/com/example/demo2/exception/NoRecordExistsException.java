package com.example.demo2.exception;

public class NoRecordExistsException extends RuntimeException{
    private String message;
    public NoRecordExistsException() {}
    public NoRecordExistsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
