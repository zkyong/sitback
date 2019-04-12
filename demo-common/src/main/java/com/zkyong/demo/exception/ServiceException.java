package com.zkyong.demo.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -7164138972480307741L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
