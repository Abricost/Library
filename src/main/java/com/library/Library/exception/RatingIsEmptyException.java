package com.library.Library.exception;

public class RatingIsEmptyException extends RuntimeException {
    public RatingIsEmptyException(String message) {
        super(message);
    }
}
