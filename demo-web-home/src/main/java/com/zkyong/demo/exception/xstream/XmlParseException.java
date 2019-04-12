package com.zkyong.demo.exception.xstream;

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
