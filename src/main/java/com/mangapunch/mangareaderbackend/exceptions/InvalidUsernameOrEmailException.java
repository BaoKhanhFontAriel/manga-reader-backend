package com.mangapunch.mangareaderbackend.exceptions;

public class InvalidUsernameOrEmailException extends Exception{
    public InvalidUsernameOrEmailException(String message) {
        super(message);
    }
}
