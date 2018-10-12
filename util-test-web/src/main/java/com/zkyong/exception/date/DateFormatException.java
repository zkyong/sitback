package com.zkyong.exception.date;


public class DateFormatException extends Exception{

    private static final long serialVersionUID = 3064445498158100909L;

    
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