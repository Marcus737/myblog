package com.wei.myblog.exception;

public class NotExistException extends RuntimeException {
    public NotExistException(String s){
        super(s);
    }
}
