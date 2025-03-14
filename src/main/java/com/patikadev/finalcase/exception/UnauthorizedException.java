// UnauthorizedException.java
package com.patikadev.finalcase.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}