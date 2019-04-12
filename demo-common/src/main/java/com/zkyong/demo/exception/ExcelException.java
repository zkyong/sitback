package com.zkyong.demo.exception;

/**
 * Excel处理异常
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:52:37
 */
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
