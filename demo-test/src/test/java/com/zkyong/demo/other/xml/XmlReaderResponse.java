package com.zkyong.demo.other.xml;

import java.lang.reflect.Method;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlReaderResponse extends XmlParserBase {

    public <T> T readXml(StringBuilder xmlbuf, Class<T> clazz) throws Throwable {

        T objData = clazz.newInstance();

        String xml = URLDecoder.decode(xmlbuf.toString(), "utf-8");
        Document document = DocumentHelper.parseText(xml);//= saxReader.read( new StringReader(xmlbuf) );
        //LOG._esb.info("数据："+formatXML(document));
        Method headMethod = null;
        //MsgResponseHead headObject=null;
        Object headObject = null;

        Element headRoot = this.getXmlElement(document, "/tx/head");
        Element responseRoot = this.getXmlElement(document, "/tx/body/Response");

        //报文head
        //headObject = readXmlChild(headRoot, MsgResponseHead.class);
        headObject = readXmlChild(headRoot, Object.class);
        //报文body
        objData = readXmlChild(responseRoot, clazz);
        if (objData == null) {
            objData = clazz.newInstance();
        }

        //headMethod = this.getMethod(clazz, "setHead", MsgResponseHead.class);
        headMethod = this.getMethod(clazz, "setHead", Object.class);
        if (headMethod != null && headObject != null) {
            headMethod.invoke(objData, headObject);
        }
        return objData;
    }

    public <T> T readXmlChild(Element parent, Class<T> clazz) throws Throwable {

        Map<String, XmlMethods> methods = getXmlMethods(clazz);
        return readXmlChild(parent, clazz, methods);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> T readXmlChild(Element parent, Class<T> clazz,
                              Map<String, XmlMethods> methods) throws Throwable {
        if (parent == null) {
            return null;
        }

        T obj = clazz.newInstance();
        Iterator<?> it = parent.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            XmlMethods xmlMethod = methods.get(element.getName());
            if (xmlMethod == null) {
                continue;
            }

            Class<?> paraClass = getSetMethodParameterType(xmlMethod);
            if (paraClass == null) {
                continue;
            }

            if (java.util.List.class.isAssignableFrom(paraClass)) {
                //list行记录
                if (xmlMethod.listGenericClazz == null || xmlMethod.getMethod == null
                    || xmlMethod.setMethod == null) {
                    continue;
                }
                List lists = (List) xmlMethod.getMethod.invoke(obj);
                if (lists == null) {
                    lists = new ArrayList();
                }
                Object row = readXmlChild(element, xmlMethod.listGenericClazz,
                    xmlMethod.listXmlMethods);
                lists.add(row);
                invokeSetMethod(xmlMethod, obj, lists);
                //}else if(MsgFieldClass.class.isAssignableFrom(paraClass)){
            } else if (Object.class.isAssignableFrom(paraClass)) {
                //对象解析
                Object retVal = readXmlChild(element, paraClass);
                invokeSetMethod(xmlMethod, obj, retVal);
            } else {
                //普通节点
                Object retVal = readXmlFieldValue(element.getText(), paraClass);
                invokeSetMethod(xmlMethod, obj, retVal);
            }
        }
        return obj;
    }

    public Object readXmlFieldValue(String s, Class<?> clazz) throws Throwable {
        return clazz;
        /*
        if (clazz.isAssignableFrom(String.class)) {
            String ret = StringUtil.filter(s);
            if (ret != null && ret.matches("^\\d{4}-(0[1-9]|1[0-2])-([0][1-9]|[1-2]\\d|3[0-1]) ([01]\\d|2[0-3])(:[0-5]\\d){2}(\\.\\d( (UTC|CST))*)*")) {
                ret = DateUtils.toDateTime(ret);
            }
            return ret;
        } else if (clazz.isAssignableFrom(Integer.class)) {
            return IntegerParser.parse(s);
        } else if (clazz.isAssignableFrom(BigDecimal.class)) {
            return BigDecimalParser.parse(s);
        } else if (clazz.isAssignableFrom(Long.class)) {
            return LongParser.parse(s);  
        } else if (clazz.isAssignableFrom(Double.class)) {
            return DoubleParser.parse(s);
        } else{
        	throw new XmlParseException("不支持此数据类型"+clazz.toString());
        }
        */
    }
}
