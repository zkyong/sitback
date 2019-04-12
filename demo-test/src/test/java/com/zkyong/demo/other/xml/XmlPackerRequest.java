package com.zkyong.demo.other.xml;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zkyong.demo.exception.XmlParseException;

public class XmlPackerRequest extends XmlParserBase {
    /**
     * 打包网银xml
     * @param obj
     * @return
     * @throws Throwable
     */
    @SuppressWarnings({ "rawtypes" })
    public StringBuilder packBankXml(Object obj) throws Throwable {

        StringBuilder xmlbuf = new StringBuilder();

        Class<?> clazz = obj.getClass();

        xmlbuf.append("<tx><head></head><body></body></tx>");
        Document document = DocumentHelper.parseText(xmlbuf.toString());
        Element headRoot = this.getXmlElement(document, "/tx/head");
        Element requestRoot = this.getXmlElement(document, "/tx/body");

        //打包数据
        Map<String, XmlMethods> methods = getXmlMethods(clazz);
        for (Entry<String, XmlMethods> entity : methods.entrySet()) {
            String name = entity.getKey();
            XmlMethods method = entity.getValue();

            Object val = method.getMethod.invoke(obj);
            if (val == null) {
                continue;
            }

            //if( val instanceof MsgRequestHead ){
            if (val instanceof Object) {
                //头部打包
                packChild(headRoot, val);
            } else if (val instanceof java.util.List) {
                //列表
                Element element = requestRoot.addElement(name);
                for (Object item : (java.util.List) val) {
                    packChild(element, item);
                }
                //}else if(val instanceof MsgFieldClass){
            } else if (val instanceof Object) {
                //对象打包
                Element element = requestRoot.addElement(name);
                packChild(element, val);
            } else {
                //元素打包
                String strVal = packXmlFieldValue(val);
                Element element = requestRoot.addElement(name);
                element.setText(strVal);
            }
        }

        xmlbuf = formatXML(document);
        return xmlbuf;
    }

    @SuppressWarnings({ "rawtypes" })
    public StringBuilder packXml(Object obj) throws Throwable {

        StringBuilder xmlbuf = new StringBuilder();

        Class<?> clazz = obj.getClass();

        xmlbuf.append("<tx><head></head><body><Request></Request></body></tx>");
        Document document = DocumentHelper.parseText(xmlbuf.toString());
        Element headRoot = this.getXmlElement(document, "/tx/head");
        Element requestRoot = this.getXmlElement(document, "/tx/body/Request");

        //打包数据
        Map<String, XmlMethods> methods = getXmlMethods(clazz);
        for (Entry<String, XmlMethods> entity : methods.entrySet()) {
            String name = entity.getKey();
            XmlMethods method = entity.getValue();

            Object val = method.getMethod.invoke(obj);
            if (val == null) {
                continue;
            }

            //if( val instanceof MsgRequestHead ){
            if (val instanceof Object) {
                //头部打包
                packChild(headRoot, val);
            } else if (val instanceof java.util.List) {
                //列表
                Element element = requestRoot.addElement(name);
                for (Object item : (java.util.List) val) {
                    packChild(element, item);
                }
                //}else if(val instanceof MsgFieldClass){
            } else if (val instanceof Object) {
                //对象打包
                Element element = requestRoot.addElement(name);
                packChild(element, val);
            } else {
                //元素打包
                String strVal = packXmlFieldValue(val);
                Element element = requestRoot.addElement(name);
                element.setText(strVal);
            }
        }

        xmlbuf = formatXML(document);
        return xmlbuf;
    }

    @SuppressWarnings("rawtypes")
    public void packChild(Element parent, Object obj) throws Throwable {
        Map<String, XmlMethods> methods = getXmlMethods(obj.getClass());
        for (Entry<String, XmlMethods> entity : methods.entrySet()) {
            String name = entity.getKey();
            XmlMethods method = entity.getValue();

            Object val = method.getMethod.invoke(obj);
            if (val == null) {
                continue;
            }

            if (val instanceof java.util.List) {
                //列表
                Element element = parent.addElement(name);
                for (Object item : (java.util.List) val) {
                    packChild(element, item);
                }
                //}else if(val instanceof MsgFieldClass){
            } else if (val instanceof Object) {
                //对象打包
                Element element = parent.addElement(name);
                packChild(element, val);
            } else {
                //元素打包
                String strVal = packXmlFieldValue(val);
                Element element = parent.addElement(name);
                element.setText(strVal);
            }
        }
    }

    public String packXmlFieldValue(Object obj) throws Throwable {
        if (obj == null) {
            return "";
        }

        if (obj instanceof String) {
            return obj.toString();
        } else if (obj instanceof Integer) {
            return String.valueOf(obj);
        } else if (obj instanceof BigDecimal) {
            BigDecimal amount = ((BigDecimal) obj).setScale(2, RoundingMode.HALF_UP);
            return amount.toString();
        } else if (obj instanceof Long) {
            return String.valueOf(obj);
        } else {
            throw new XmlParseException("不支持此数据类型" + obj.getClass().toString());
        }
    }
}
