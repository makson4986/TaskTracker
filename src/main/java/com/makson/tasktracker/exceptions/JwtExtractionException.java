package com.makson.tasktracker.exceptions;

public class JwtExtractionException extends RuntimeException {
    public JwtExtractionException() {
        super();
    }

    public JwtExtractionException(String message) {
        super(message);
    }

    public JwtExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtExtractionException(Throwable cause) {
        super(cause);
    }
}
