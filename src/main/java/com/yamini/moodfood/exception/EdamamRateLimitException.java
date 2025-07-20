package com.yamini.moodfood.exception;


public class EdamamRateLimitException extends RuntimeException {
    public EdamamRateLimitException(String message)
    {
        super(message);
    }
}