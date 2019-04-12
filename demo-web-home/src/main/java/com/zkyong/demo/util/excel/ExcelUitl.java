package com.zkyong.demo.util.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.zkyong.demo.exception.excel.ExcelException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @Description Excel导入导出工具类<BR>
 * @author zkyong<BR>
 * @date  2017年9月30日 11:45:53<BR>
 * @Version : 1.0.0<BR>
 */
public class ExcelUitl {
    /** 格式化时间：yyyyMMddhhmmss */
    private static SimpleDateFormat FORMAT_DATE_YMDHMS = new SimpleDateFormat("yyyyMMddhhmmss");
    
    /**
     * @MethodName : listToExcel<BR>
     * @Description : 将List的数据转换为Execel表导出到本地文件系统<BR>
     * @param list 数据源<BR>
     * @param fieldMap
     *          类的属性和Excel的列名对应关系。<BR>
     *          如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式<BR>
     *          如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写 
     *          fieldMap.put("college.collegeName","学院名称") <BR>
     * @param sheetName 工作表名称<BR>
     * @param sheetSize 每个工作表中最大记录数<BR>
     * @param out 输出流<BR>
     * @throws ExcelException<BR>
     */
    public static <T> void listToExcel(
            List<T> list, 
            LinkedHashMap<String, String> fieldMap, 
            String sheetName,
            int sheetSize, 
            OutputStream out
            ) throws ExcelException {

        if (list.size() == 0 || list == null) {
            throw new ExcelException("数据源中没有任何数据");
        }

        if (sheetSize > 65535 || sheetSize < 1) {
            sheetSize = 65535;
        }

        // 创建工作簿并发送到OutputStream指定的地方
        WritableWorkbook wwb;
        try {
            wwb = Workbook.createWorkbook(out);
            // 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条  
            // 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程  
            // 1.计算一共有多少个工作表 
            double sheetNum = Math.ceil(list.size() / new Double(sheetSize));

            // 2.创建相应的工作表，并向其中填充数据 
            int maxIndex = list.size() - 1;
            for (int i = 0; i < sheetNum; i++) {
                    WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);
                    int minIndexCurrent = i * sheetSize;// 当前工作表最小索引(起始索引)
                    int maxIndexCurrent = minIndexCurrent + sheetSize -1;// 当前工作表最大索引(不一定是结束索引)
                    int endIndexCurrent = maxIndexCurrent > maxIndex ? maxIndex : maxIndexCurrent;// 结束索引
                    // 填充工作表  
                    fillSheet(sheet, list, fieldMap, minIndexCurrent, endIndexCurrent);
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ExcelException) {
                throw (ExcelException) e;
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }
    }

    /**
     * @MethodName : listToExcel<BR>
     * @Description : 将List的数据转换为Execel表导出到本地文件系统,工作表最大记录数为Excel允许的最大值65536(除去表头65535)<BR>
     * @param list 数据源<BR>
     * @param fieldMap 类的属性(key)和Excel的列名(value)对应关系 <BR>
     * @param out 输出流<BR>
     * @throws ExcelException<BR>
     */
    public static <T> void listToExcel(
            List<T> list, 
            LinkedHashMap<String, String> fieldMap, 
            String sheetName,
            OutputStream out
            ) throws ExcelException {
        listToExcel(list, fieldMap, sheetName, 65535, out);
    }

    /**
     * @MethodName : listToExcel<BR>
     * @Description : 将List的数据转换为Execel表导出到浏览器<BR>
     * @param list 数据源<BR>
     * @param fieldMap 类的属性和Excel的列名对应关系 <BR>
     * @param sheetSize 每个工作表中最大记录数<BR>
     * @param response 响应对象<BR>
     * @throws ExcelException
     */
    public static <T> void listToExcel(
            List<T> list, 
            LinkedHashMap<String, String> fieldMap, 
            String sheetName,
            int sheetSize, 
            HttpServletResponse response
            ) throws ExcelException {

        // 设置默认文件名为当前时间：年月日时分秒  
        String fileName = FORMAT_DATE_YMDHMS.format(new Date()).toString();

        // 设置response头信息  
        response.reset();
        response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件  
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

        // 创建工作簿并发送到浏览器  
        try {
            OutputStream out = response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, sheetSize, out);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ExcelException) {
                throw (ExcelException) e;
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }
    }

    /**
     * @MethodName : listToExcel
     * @Description : 将List的数据转换为Execel表导出到浏览器,工作表最大记录数为Excel允许的最大值65536(除去表头65535)<BR>
     * @param list 数据源<BR>
     * @param fieldMap 类的属性和Excel的列名对应关系 <BR>
     * @param response 响应对象<BR>
     * @throws ExcelException
     */
    public static <T> void listToExcel(
            List<T> list, 
            LinkedHashMap<String, String> fieldMap, 
            String sheetName,
            HttpServletResponse response
            ) throws ExcelException {
        listToExcel(list, fieldMap, sheetName, 65535, response);
    }

    /**
     * @MethodName : excelToList<BR>
     * @Description : 将Excel中的数据转化为List<BR>
     * @param in : 输入流<BR>
     * @param sheetName : 工作表名称<BR>
     * @param entityClass : List中对象的类型
     * @param fieldMap : Excel中列名(key)和类的属性(value)的对应关系
     * @param uniqueFields : 指定业务主键组合(即复合主键)
     * @return List
     * @throws ExcelException
     */
    public static <T> List<T> excelToList(
            InputStream in, 
            String sheetName, 
            Class<T> entityClass,
            LinkedHashMap<String, String> fieldMap, 
            String[] uniqueFields
            ) throws ExcelException {

        // 定义要返回的list
        List<T> resultList = new ArrayList<T>();
        try {
            // 根据Excel数据源创建Workbook  
            Workbook wb = Workbook.getWorkbook(in);
            // 获取工作表
            Sheet sheet = wb.getSheet(sheetName);
            // 获取工作表的有效行数 
            int realRows = 0;
            for (int i = 0; i < sheet.getRows(); i++) {
                int nullCols = 0;
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell currentCell = sheet.getCell(j, i);
                    if (currentCell == null || "".equals(currentCell.getContents().toString())) {
                        nullCols++;
                    }
                }

                if (nullCols == sheet.getColumns()) {
                    break;
                } else {
                    realRows++;
                }
            }

            // 如果Excel中没有数据则提示错误
            if (realRows <= 1) {
                throw new ExcelException("Excel文件中没有任何数据");
            }
            Cell[] firstRow = sheet.getRow(0);
            String[] excelFieldNames = new String[firstRow.length];
            // 获取Excel中的列名
            for (int i = 0; i < firstRow.length; i++) {
                excelFieldNames[i] = firstRow[i].getContents().toString().trim();
            }

            // 判断需要的字段在Excel中是否都存在
            List<String> excelFieldList = Arrays.asList(excelFieldNames);
            for (String cnName : fieldMap.keySet()) {
                if (!excelFieldList.contains(cnName)) {
                    throw new ExcelException("Excel中缺少必要的字段，或字段名称有误");
                }
            }

            // 将列名和列号放入Map中,这样通过列名就可以拿到列号  
            LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < excelFieldNames.length; i++) {
                colMap.put(excelFieldNames[i], firstRow[i].getColumn());
            }

            // 判断是否有重复行  
            // 1.获取uniqueFields指定的列
            Cell[][] uniqueCells = new Cell[uniqueFields.length][];
            for (int i = 0; i < uniqueFields.length; i++) {
                int col = colMap.get(uniqueFields[i]);
                uniqueCells[i] = sheet.getColumn(col);
            }

            // 2.从指定列中寻找重复行
            for (int i = 1; i < realRows; i++) {
                int nullCols = 0;
                for (int j = 0; j < uniqueFields.length; j++) {
                    String currentContent = uniqueCells[j][i].getContents();
                    Cell sameCell = sheet.findCell(currentContent, uniqueCells[j][i].getColumn(),
                            uniqueCells[j][i].getRow() + 1, uniqueCells[j][i].getColumn(),
                            uniqueCells[j][realRows - 1].getRow(), true);
                    if (sameCell != null) {
                        nullCols++;
                    }
                }

                if (nullCols == uniqueFields.length) {
                    throw new ExcelException("Excel中有重复行，请检查");
                }
            }

            // 将sheet转换为list
            for (int i = 1; i < realRows; i++) {
                // 新建要转换的对象  
                T entity = entityClass.newInstance();
                // 给对象中的字段赋值
                for (Entry<String, String> entry : fieldMap.entrySet()) {
                    // 获取Excel列名
                    String cnNormalName = entry.getKey();
                    // 获取类的属性名
                    String enNormalName = entry.getValue();
                    // 根据列名获取列号  
                    int col = colMap.get(cnNormalName);
                    // 获取当前单元格中的内容
                    String content = sheet.getCell(col, i).getContents().toString().trim();
                    // 给对象赋值
                    setFieldValueByName(enNormalName, content, entity);
                }
                resultList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ExcelException) {
                throw (ExcelException) e;
            } else {
                e.printStackTrace();
                throw new ExcelException("从Excel导入失败");
            }
        }
        return resultList;
    }

    /* <---------------------------------辅助的私有方法---------------------------------> */

    /** 
     * @MethodName : getFieldByName<BR>
     * @Description : 根据字段名获取字段<BR>
     * @param fieldName : 字段名<BR>
     * @param clazz : 包含该字段的类<BR>
     * @return : 字段<BR>
     */  
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段  
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存在该字段,则返回  
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则查看父类中是否存在此字段,如果有则返回  
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本类和父类都没有,则返回空
        return null;
    }

    /**
     * @MethodName : getFieldValueByNameSequence<BR>
     * @Description : 根据带路径或不带路径的属性名获取属性值,即接受简单属性名,如userName等;
     *                又接受带路径的属性名,如student.department.name等<BR>
     * @param fieldNameSequence 带路径的属性名或简单属性名<BR>
     * @param o : 对象<BR>
     * @return : 属性值<BR>
     * @throws Exception<BR>
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {

        Object value = null;

        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据属性名获取属性对象
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;

    }

    /** 
     * @MethodName : setFieldValueByName<BR>
     * @Description : 根据字段名给对象的字段赋值<BR>
     * @param fieldName : 字段名<BR>
     * @param fieldValue : 字段值<BR>
     * @param o : 对象<BR>
     */  
    private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {
        String[] fieldNames = fieldName.split("\\.");
        Field field = getFieldByName(fieldNames[0], o.getClass());
        
        Object fieldObj = null;
        // 若字段名可以用.分割为多个,则该属性是自建类
        if(fieldNames.length > 1){
           // 获取该属性的值
           fieldObj = getFieldValueByName(fieldNames[0], o);
            // 若该属性的值为null,则先实例化
            if(null == fieldObj) {
                fieldObj = field.getType().newInstance();
            }
            //获取该属性(对象)的属性
            String nextFieldName = fieldName.substring(fieldNames[0].length()+1);
            // 递归调用该方法
            setFieldValueByName(nextFieldName, fieldValue, fieldObj);
        }
        
        if (field != null) {
            field.setAccessible(true);
            // 获取字段类型
            Class<?> fieldType = field.getType();
            // 根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
                }
            } else if (Date.class == fieldType) {
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            } else {
                field.set(o, fieldObj);
            }
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名" + fieldName);
        }
    }

    /** 
     * @MethodName : getFieldValueByName 
     * @Description : 根据字段名获取字段值 
     * @param fieldName : 字段名 
     * @param o : 对象 
     * @return : 字段值 
     */  
    private static Object getFieldValueByName(String fieldName, Object o) throws Exception {
        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名" + fieldName);
        }

        return value;
    }

    /** 
     * @MethodName  : setColumnAutoSize 
     * @Description : 设置工作表自动列宽和首行加粗 
     * @param ws 
     */  
    private static void setColumnAutoSize(WritableSheet ws, int extraWith) {
        // 获取本列的最宽单元格的宽度
        for (int i = 0; i < ws.getColumns(); i++) {
            int colWith = 0;
            for (int j = 0; j < ws.getRows(); j++) {
                String content = ws.getCell(i, j).getContents().toString();
                int cellWith = content.length();
                if (colWith < cellWith) {
                    colWith = cellWith;
                }
            }
            // 设置单元格的宽度为最宽宽度+额外宽度
            ws.setColumnView(i, colWith + extraWith);
        }
    }
      
    /** 
     * @MethodName : fillSheet 
     * @Description : 向工作表中填充数据 
     * @param sheet : 工作表  
     * @param list : 数据源 
     * @param fieldMap : 中英文字段对应关系的Map 
     * @param firstIndex : 开始索引 
     * @param lastIndex : 结束索引 
     */  
    private static <T> void fillSheet(WritableSheet sheet, List<T> list, LinkedHashMap<String, String> fieldMap,
            int firstIndex, int lastIndex) throws Exception {

        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        // 填充数组
        int count = 0;
        for (Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }
        // 填充表头
        for (int i = 0; i < cnFields.length; i++) {
            Label label = new Label(i, 0, cnFields[i]);
            sheet.addCell(label);
        }

        // 填充内容
        int rowNo = 1;
        for (int index = firstIndex; index <= lastIndex; index++) {
            // 获取单个对象
            T item = list.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = objValue == null ? "" : objValue.toString();
                Label label = new Label(i, rowNo, fieldValue);
                sheet.addCell(label);
            }
            rowNo++;
        }
        // 设置自动列宽
        setColumnAutoSize(sheet, 5);
    }
}  