package com.zkyong.util.xml;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.zkyong.exception.xstream.XmlParseException;


public class XmlParserBase {
	
	public Element getXmlElement(Document document, String path) throws Throwable{
		Element root = document.getRootElement();
		return getXmlElement(root, path);
	}
	
	public Element getXmlElement(Element root, String path) throws Throwable{
		if(root == null){
			return null;
		}
		Iterator<?> it = root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			String url = element.getPath();
			if(url.equals(path)){
				return element;
			}
			Element retElement = getXmlElement(element, path);
			if(retElement != null){
				return retElement;
			}
		}
		return null;
	}
	
	public Map<String, XmlMethods> getXmlMethods(Class<?> clazz) throws Throwable{

		Map<String, XmlMethods> xmlMethods = new HashMap<String, XmlMethods>();
		if(clazz == null){
			return xmlMethods;
		}
		getXmlMethodsSupper(xmlMethods, clazz);

		return xmlMethods;
	}
	
	private void getXmlMethodsSupper(Map<String, XmlMethods> xmlMethods, Class<?> clazz) throws Throwable{

		if(clazz == null){
			return ;
		}
		
		Field fields[] = clazz.getDeclaredFields();
		if(fields == null){
			return ;
		}
		
		for(Field field:fields){
			XmlMethods xmlMethod = new XmlMethods();
			
			xmlMethod.fieldClass = field.getDeclaringClass();
			try{
				String methodName = "get"+captureName(field.getName());
				xmlMethod.getMethod = clazz.getDeclaredMethod( methodName);
			}catch(NoSuchMethodException e){
				//e.printStackTrace();
			}
			
			try{
				String methodName = "set"+captureName(field.getName());
				xmlMethod.setMethod = clazz.getDeclaredMethod( methodName, field.getType() );
			}catch(NoSuchMethodException e){
				//e.printStackTrace();
			}
			
			//泛型
			Type fc = field.getGenericType();
			if(fc != null){
				if( (fc instanceof ParameterizedType) && 
					field.getType().isAssignableFrom(java.util.List.class) ){
					ParameterizedType pt = (ParameterizedType)fc;
					xmlMethod.listGenericClazz = (Class<?>)pt.getActualTypeArguments()[0];
					xmlMethod.listXmlMethods = getXmlMethods(xmlMethod.listGenericClazz);
				}
			}
			
			if(xmlMethod.setMethod != null && xmlMethod.getMethod != null && xmlMethod.fieldClass != null){
				xmlMethods.put(field.getName(), xmlMethod);
			}
		}
		
		Class<?> supper = clazz.getSuperclass();
		if(!supper.isAssignableFrom(Object.class)){
			getXmlMethodsSupper(xmlMethods, supper);
		}

		return ;
	}

	public Map<String, Method> getMethods(Class<?> clazz, String prefix) throws Throwable{

		if(clazz == null){
			return null;
		}
		
		Map<String, Method> methods = new HashMap<String, Method>();
		getMethodsSupper(methods, clazz, prefix);

		return methods;
	}
	
	private void getMethodsSupper(Map<String, Method> methods, Class<?> clazz, String prefix) throws Throwable{

		if(clazz == null){
			return ;
		}
		
		Field fields[] = clazz.getDeclaredFields();
		if(fields == null){
			return ;
		}
		
		for(Field field:fields){
			String methodName = prefix+captureName(field.getName());
			Method method = null;
			try{
				if("get".equals(prefix)){
					method = clazz.getDeclaredMethod( methodName);
				}else{
					method = clazz.getDeclaredMethod( methodName, field.getType() );
				}
			}catch(NoSuchMethodException e){
				//e.printStackTrace();
				continue;
			}
			
			if(method != null){
				methods.put(field.getName(), method);
			}
		}
		
		Class<?> supper = clazz.getSuperclass();
		if(!supper.isAssignableFrom(Object.class)){
			getMethodsSupper(methods, supper, prefix);
		}

		return ;
	}
	
	private String captureName(String name) {
		char[] cs=name.toCharArray();
		if(!Character.isUpperCase(cs[0])){
			cs[0]-=32;
		}
		return String.valueOf(cs);
	}
	
	@SuppressWarnings("unused")
	private Class<?> getXmlMsgRowEntity(Field field) throws Throwable{
		Type mapMainType = field.getGenericType();
		if (mapMainType instanceof ParameterizedType) {  
            // 执行强制类型转换  
            ParameterizedType parameterizedType = (ParameterizedType)mapMainType;  
            // 获取基本类型信息，即Map  
            Type basicType = parameterizedType.getRawType();  
            //System.out.println("基本类型为："+basicType);  
            // 获取泛型类型的泛型参数  
            Type[] types = parameterizedType.getActualTypeArguments();  
            for (int i = 0; i < types.length; i++) {
            	return (Class<?>)(types[i]);
            }
            throw new XmlParseException("获取泛型类型出错2!");
        } else {  
            throw new XmlParseException("获取泛型类型出错!");  
        }
	}
	
	public Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes){
		try{
			return clazz.getMethod(name, parameterTypes);
		}catch(Throwable e){
			return null;
		}
	}
	
	protected StringBuilder formatXML(Document document) throws Throwable{
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setExpandEmptyElements(true);
		format.setTrimText(false);
		format.setIndent(true);      // 设置是否缩进
		format.setIndent("	");
		format.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(stringWriter, format);
		xmlWriter.write(document);  
		xmlWriter.flush();
		xmlWriter.close();
		StringBuilder xmlbuf = new StringBuilder( stringWriter.getBuffer() );
		return xmlbuf;
	}
	
	public Class<?> getSetMethodParameterType(XmlMethods xmlMethods){
		if(xmlMethods == null){
			return null;
		}
		if(xmlMethods.setMethod == null){
			return null;
		}
		Class<?> paraClass = xmlMethods.setMethod.getParameterTypes()[0];
		return paraClass;
	}
	
	public void invokeSetMethod(XmlMethods xmlMethods, Object obj, Object val) throws Throwable{
		if( xmlMethods == null ||
			xmlMethods.setMethod == null ||
			obj == null ||
			val == null ){
			return ;
		}
		xmlMethods.setMethod.invoke(obj, val);
	}
}
