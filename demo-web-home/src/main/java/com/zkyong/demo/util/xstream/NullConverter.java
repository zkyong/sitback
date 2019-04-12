//package com.zkyong.demo.util.xstream;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.util.List;
//
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.converters.MarshallingContext;
//import com.thoughtworks.xstream.converters.UnmarshallingContext;
//import com.thoughtworks.xstream.io.HierarchicalStreamReader;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//
//public class NullConverter implements Converter {
//    
//    @SuppressWarnings("rawtypes")
//    public void marshal(Object source, HierarchicalStreamWriter writer,
//            MarshallingContext context) {
//        if (null == source)
//            return;
//       
//        Class cType = source.getClass();
//        Field[] fields = cType.getDeclaredFields();
//        if (source instanceof List) {
//            List list = (List) source;
//            for (Object obj : list) {
//                XStreamAlias alias = obj.getClass().getAnnotation(XStreamAlias.class);
//                if (alias != null) {
//                    writer.startNode(alias.value());
//                    marshal(obj, writer, context);
//                    writer.endNode();
//                }else {
//                    marshal(obj, writer, context);
//                }
//            }
//        } else {
//            for (Field field : fields) {
//            //获得get方法
//                String temp1 = "get"
//                        + field.getName().substring(0, 1).toUpperCase()
//                        + field.getName().substring(1);
//                Method m = null;
//                try {
//                    m = cType.getMethod(temp1, null);
//                } catch (SecurityException e1) {
//                    e1.printStackTrace();
//                } catch (NoSuchMethodException e1) {
//                    e1.printStackTrace();
//                }
//                String methodName = m.getName();
//                if (methodName.indexOf("get") != -1 && methodName != "getClass") {
//                    boolean isBaseType = isBaseType(m.getReturnType());
//                    String name = methodName.substring(3);
//                    Object o = null;
//                    try {
//                        o = m.invoke(source, null);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    //递归打出基础类型值
//                    if (isBaseType) {
//                            writer.startNode(getAliasByNameAndType(name, cType).value());
//                            writeData(o, m.getReturnType(), writer);
//                            writer.endNode();
//                    } else {
//                        XStreamAlias alias = getAliasByNameAndType(name, cType);
//                            if (alias == null) {
//                                marshal(o, writer, context);
//                            } else {
//                                writer.startNode(alias.value());
//                                marshal(o, writer, context);
//                                writer.endNode();
//                            }
//                    }
//                }
//            }
//        }
//    }
// 
//    /**
//     * 根据Name和类获得Xstream注解
//     * @param name
//     * Name
//     * @param cType
//     * 类
//     * @return
//     * XStreamAlias
//     */
//    private XStreamAlias getAliasByNameAndType(String name,Class<?> cType){
//        String temp = name.substring(0, 1).toLowerCase()
//        + name.substring(1);
//        Field f = null;
//        try {
//            f = cType.getDeclaredField(temp);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        XStreamAlias alias = f.getAnnotation(XStreamAlias.class);
//        return alias;
//    }
//     
//    /**
//     * 改写输出XML
//     * @param o
//     * @param ReturnType
//     * @param writer
//     */
//    private void writeData(Object o,Class<?> ReturnType,HierarchicalStreamWriter writer) {
//        //如果是数字类型的话就要预设为0而不能为空
//        if (isNumValueType(ReturnType)) {
//            if (o == null) {
//                writer.setValue("0");
//            }else if (ReturnType.equals(Double.class)||ReturnType.equals(double.class)||ReturnType.equals(BigDecimal.class)) {
//                DecimalFormat df = new DecimalFormat("#.##");
//                writer.setValue(df.format(o));
//            }else {
//                writer.setValue(o.toString());
//            }
//        } else {
//            writer.setValue(o == null ? "" : o.toString());
//        }
//    }
//     
//    public Object unmarshal(HierarchicalStreamReader reader,
//            UnmarshallingContext context) {
//        return null;
//    }
// 
//    public boolean canConvert(Class type) {
//        return true;
//    }
// 
// 
//    /**
//     * 判断是否为基本类型
//     * @param type
//     * @return
//     * boolean
//     */
//    private boolean isBaseType(Class<?> type) {
//        if (type.equals(Integer.class) || type.equals(Double.class)
//                || type.equals(String.class) || type.equals(Boolean.class)
//                || type.equals(Long.class) || type.equals(Short.class)
//                || type.equals(Byte.class) || type.equals(Float.class)
//                || type.equals(BigDecimal.class) || type.equals(int.class)
//                || type.equals(float.class) || type.equals(long.class)
//                || type.equals(double.class) || type.equals(short.class)
//                || type.equals(boolean.class) || type.equals(byte.class)) {
//            return true;
//        }
//        return false;
//    }
// 
//        /**
//     * 判断是否为数字类型
//     * @param type
//     * @return
//     * boolean
//     */
//    public boolean isNumValueType(Class<?> type) {
//        if (type.equals(Integer.class) || type.equals(Double.class)
//                || type.equals(Long.class) || type.equals(Short.class)
//                || type.equals(Float.class) || type.equals(BigDecimal.class)
//                || type.equals(int.class) || type.equals(float.class)
//                || type.equals(long.class) || type.equals(double.class)
//                || type.equals(short.class)) {
//            return true;
//        }
//        return false;
//    }
// 
//}