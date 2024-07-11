package com.horizonx.test.exceptions;

public class EntityAlreadyExistException extends  RuntimeException{
    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
