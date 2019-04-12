package com.zkyong.demo.other.xml;

import java.lang.reflect.Method;
import java.util.Map;

public class XmlMethods {

    public Method                  setMethod;
    public Method                  getMethod;
    public Class<?>                fieldClass;
    public Class<?>                listGenericClazz; //list泛型
    public Map<String, XmlMethods> listXmlMethods;
}
