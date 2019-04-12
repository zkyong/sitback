package com.zkyong.demo.exception;

/**
 * 时间格式化异常
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:52:23
 */
public class DateFormatException extends Exception {

    private static final long serialVersionUID = 3064445498158100909L;

    public DateFormatException() {
    }

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(Throwable cause) {
        super(cause);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
