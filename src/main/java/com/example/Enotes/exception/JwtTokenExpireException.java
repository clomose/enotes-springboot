package com.example.Enotes.exception;

public class JwtTokenExpireException extends RuntimeException{

    public JwtTokenExpireException(String message){
        super(message);
    }
}
