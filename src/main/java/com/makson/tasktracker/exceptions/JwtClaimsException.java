package com.makson.tasktracker.exceptions;

public class JwtClaimsException extends RuntimeException {
    public JwtClaimsException() {
        super();
    }

    public JwtClaimsException(String message) {
        super(message);
    }

    public JwtClaimsException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtClaimsException(Throwable cause) {
        super(cause);
    }
}
