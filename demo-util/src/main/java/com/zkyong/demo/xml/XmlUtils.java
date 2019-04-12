/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.zkyong.demo.xml;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Parent;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.zkyong.demo.file.FileUtils;
import com.zkyong.demo.string.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XML解析工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年4月12日 上午11:51:49
 */
public class XmlUtils {

	/** 禁用DOCTYPE声明 */
	private static final String DDD = "http://apache.org/xml/features/disallow-doctype-decl";
	/** $取值正则 */
	private static final String DOLLAR_VALUE_REGEX = "\\$\\{[a-zA-Z0-9._]*\\}";
    /** jdom位置路径--input */
    private static final String JDOM_XPATH_INPUT = "//input";
	/** 节点名称--property */
	private static final String ELEMENT_NAME_PROPERTY = "property";
	/** 节点名称--loadproperties */
	private static final String ELEMENT_NAME_LOADPROPERTIES = "loadproperties";
	/** 节点属性名称--addproperty */
	private static final String ELEMENT_ATTR_ADDPROPERTY = "addproperty";
	/** 节点属性名称--defaultvalue */
	private static final String ELEMENT_ATTR_DEFAULTVALUE = "defaultvalue";
	/** 节点属性名称--srcFile */
	private static final String ELEMENT_ATTR_SRCFILE = "srcFile";
	/** 文件路径Key */
	private static final String CURRENT_PATH_KEY = "currentPath";
	/** 普通常量--name */
	private static final String NAME = "name";
	/** 普通常量--value */
	private static final String VALUE = "value";
	/** 普通常量--星号 */
	private static final String EVERY = "*";

	@SuppressWarnings("unchecked")
	public static <T> T xml2Obj(String filePath, Class<T> clazz) throws IOException {
		String xml = FileUtils.readFile(filePath);
		XStream xstream = new XStream(new DomDriver()) {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@SuppressWarnings("rawtypes")
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(clazz);
		return (T) xstream.fromXML(xml);
	}

	/**
	 * 从xml文件中加载配置信息，包含loadproperties和property中的内容
	 *
	 * @param currentPath 当前路径(./)
	 * @param filePath 待加载xml文件路径
	 * @return Xml文件中所有配置信息的Map
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static Map<String, String> loadPropertiesFromXml(String filePath, String currentPath) throws JDOMException, IOException {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(CURRENT_PATH_KEY, currentPath);
		
		SAXBuilder builder = new SAXBuilder();
		builder.setFeature(DDD, true);
		Document doc = builder.build(new File(filePath));
		
		load(doc.getRootElement(), resultMap);
		return resultMap;
	}

	/**
	 * 加载配置
	 *
	 * @param e 节点元素
	 * @param resultMap 目标Map
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void load(Element e, Map<String, String> resultMap) throws IOException {
		// ROOT节点中的属性
		if (e.isRootElement() && e.getAttributes().size() > 0) {
			Iterator<Attribute> it_attr = e.getAttributes().iterator();
			while (it_attr.hasNext()) {
				Attribute attribute = it_attr.next();
				resultMap.put(attribute.getName(), e.getAttributeValue(attribute.getName()));
			}
		}
		// 其他属性解析
		List<Element> children = e.getChildren();
		Iterator<Element> it = children.iterator();
		while (it.hasNext()) {
			Element child = it.next();
			String name = child.getName();
			// 如果是属性节点，则读取属性
			if (ELEMENT_NAME_PROPERTY.equals(name)) {
				List<Attribute> attributes = child.getAttributes();
				String attrname = null;
				String attrvalue = null;
				for (Attribute attribute : attributes) {
					switch (attribute.getName()) {
						case NAME:
							attrname = attribute.getValue();
							break;
						case VALUE:
							attrvalue = attribute.getValue();
							break;
						default:
							break;
					}
				}
				Pattern pattern = Pattern.compile(DOLLAR_VALUE_REGEX);
				Matcher matcher = pattern.matcher(attrvalue);
				while (matcher.find()) {
					String item = matcher.group();
					String key = item.substring(2, item.length() - 1);
					attrvalue = attrvalue.replace(item, resultMap.get(key));
				}
				resultMap.put(attrname, attrvalue);
				continue;
			}
			// 如果是配置加载节点，则加载配置
			if (ELEMENT_NAME_LOADPROPERTIES.equals(name)) {
				// 获取srcFile属性值(即Properties文件的路径)
				List<Attribute> attributes = child.getAttributes();
				String attrvalue = null;
				for (Attribute attribute : attributes) {
					if (ELEMENT_ATTR_SRCFILE.equals(attribute.getName())) {
						attrvalue = parseValue(resultMap, attribute.getValue());
					}
				}
				// 加载Properties配置文件
				if (!StringUtil.isEmpty(attrvalue)) {
					Properties prop = new Properties();
					String currentPath = resultMap.get(CURRENT_PATH_KEY);
					prop.load(new FileInputStream(FileUtils.parsePath(currentPath, attrvalue)));
					for (String key : prop.stringPropertyNames()) {
						resultMap.put(key, prop.getProperty(key));
					}
				}
				continue;
			}

			// 如果有子节点，递归加载
			if (child.getChildren().size() > 0) {
				load(child, resultMap);
			}
		}
	}

	/**
	 * 解析${变量名}的值
	 *
	 * @param map 数据缓存Map
	 * @param sourceValue ${变量名}
	 * @return 解析结果
	 */
	private static String parseValue(Map<String, String> map, String sourceValue) {
		if (null == sourceValue) {
			return null;
		}
		Pattern pattern = Pattern.compile(DOLLAR_VALUE_REGEX);
		Matcher matcher = pattern.matcher(sourceValue);
		while (matcher.find()) {
			String item = matcher.group();
			String key = item.substring(2, item.length() - 1);
			if (null != map.get(key)) {
				sourceValue = sourceValue.replace(item, map.get(key));
			}
		}
		return sourceValue;
	}

    /**
     * 将xml中的input标签替换为property标签
     * 当该标签未用属性接收输入值时，直接去除标签
     * 
     * @param xmlPath xml文件路径
     * @throws IOException 
     * @throws JDOMException 
     */
    public static void input2Property(String xmlPath) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature(DDD, true);
        Document doc = builder.build(new File(xmlPath));
        
        // 匹配所有的input标签
        XPathExpression<Element> xpath = XPathFactory.instance().compile(JDOM_XPATH_INPUT, Filters.element());
        List<Element> inputList = xpath.evaluate(doc.getRootElement());
        for (Element input : inputList) {
            // 获取input标签输入的属性名称
            String name = input.getAttribute(ELEMENT_ATTR_ADDPROPERTY) == null ? null
                : input.getAttribute(ELEMENT_ATTR_ADDPROPERTY).getValue();
            String value = input.getAttribute(ELEMENT_ATTR_DEFAULTVALUE) == null ? EVERY
                : input.getAttribute(ELEMENT_ATTR_DEFAULTVALUE).getValue();
            Parent parent = input.getParent();
            if (!StringUtil.isEmpty(name)) {
                // 获取input标签输入的默认值，不存在默认值时取*
                Element property = new Element(ELEMENT_NAME_PROPERTY);
                property.setAttribute(NAME, name);
                property.setAttribute(VALUE, value);
                parent.addContent(parent.indexOf(input), property);
            }
            parent.removeContent(input);
        }
        // 修改完毕后输出
        XMLOutputter out = new XMLOutputter();
        out.setFormat(out.getFormat().setEncoding("UTF-8"));
        out.output(doc, new FileOutputStream(new File(xmlPath)));
    }
}