package com.dev2prod.demo.exceptions;

public class EntityAlreadyExistException extends  RuntimeException{
    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
