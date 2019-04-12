package com.zkyong.demo.exception;

/**
 * XML解析异常
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:53:06
 */
public class XmlParseException extends Exception {

    private static final long serialVersionUID = -6012991650284286127L;

    public XmlParseException() {
    }

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
