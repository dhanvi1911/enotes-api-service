package com.dhanvi.enotes_api_service.exception;

public class JwtExpiredTokenException extends RuntimeException{

    public JwtExpiredTokenException(String message) {
        super(message);
    }
}
