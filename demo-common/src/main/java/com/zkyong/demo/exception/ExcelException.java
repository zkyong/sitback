package com.zkyong.demo.exception;

public class ExcelException extends Exception {

    private static final long serialVersionUID = -7164138972480307741L;

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
