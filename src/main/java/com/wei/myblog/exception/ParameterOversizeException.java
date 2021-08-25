package com.wei.myblog.exception;

/**
 * 参数过大异常
 */
public class ParameterOversizeException extends RuntimeException {
    public ParameterOversizeException(String s){
        super(s);
    }
}
