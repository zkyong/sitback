package com.zkyong.demo.exception;

/**
 * 服务异常
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:52:56
 */
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
