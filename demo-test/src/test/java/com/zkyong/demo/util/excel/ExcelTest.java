package com.zkyong.demo.util.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.zkyong.demo.excel.ExcelUitl;
import com.zkyong.demo.exception.ExcelException;

/**
 * ExcelUtil Excel处理工具<BR>
 * @author zkyong<BR>
 * @date 2018年2月6日 11:05:54<BR>
 * @version 1.0.0<BR>
 */
public class ExcelTest {

    public static void main(String[] args) {
        String fullPath = "C:/Users/zkyong/Desktop/测试表格.xls " ; 
        listToExcelTest(fullPath);
        System.out.println(","+excelToListTest(fullPath));
        //String str = "college";
        //System.out.println(str.split("\\."));
    }
    
    /**
     * 将对象list转换为Excel表格<BR>
     * @param fullPath 全路径<BR>
     */
    private static void listToExcelTest(String fullPath) {
        Student student = null;
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 198 ; i++) {
            student = new Student();
            student.setId("ID000"+(i+1));
            student.setName("姓名"+(i+1));
            student.setSex(new BigDecimal(Math.random()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
            student.setScore(new BigDecimal(Math.random()*50).intValue()+50);
            student.setAge(new BigDecimal(Math.random()*3).intValue() + 15);
            College college = new College();
            college.setCollegeId(new BigDecimal(Math.random()*5).intValue());
            college.setCollegeName("学院名称"+college.getCollegeId());
            School school = new School();
            school.setSchoolId("SL2010100");
            school.setSchoolName("学校名称");
            college.setSchool(school);
            student.setCollege(college);
            list.add(student);
            System.out.println(student);
        }
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();;
        fieldMap.put("id", "学生ID");
        fieldMap.put("name", "姓名");
        fieldMap.put("sex", "性别");
        fieldMap.put("age", "年龄");
        fieldMap.put("score", "分数");
        fieldMap.put("college.collegeId", "学院ID");
        fieldMap.put("college.collegeName", "学院名称");
        fieldMap.put("college.school.schoolId", "学校ID");
        fieldMap.put("college.school.schoolName", "学校名称");
        OutputStream os = null;
        try {
            os = new  FileOutputStream(fullPath);
            ExcelUitl.listToExcel(list, fieldMap, "学生表", 200, os);
        } catch (ExcelException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 将Excel表格转换为对象list<T><BR>
     * @param fullPath ȫ·��<BR>
     */
    private static List<Student> excelToListTest(String fullPath) {
        InputStream in = null;
        List<Student> list = null;
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("学生ID", "id");
        fieldMap.put("姓名", "name");
        fieldMap.put("性别", "sex");
        fieldMap.put("年龄", "age");
        fieldMap.put("分数", "score");
        fieldMap.put("学院ID", "college.collegeId");
        fieldMap.put("学院名称", "college.collegeName");
        fieldMap.put("学校ID","college.school.schoolId");
        fieldMap.put("学校名称","college.school.schoolName");
        
        String[] uniqueFields = {"学生ID"};
        try {
            in = new FileInputStream(fullPath);
            list = ExcelUitl.excelToList(in,"学生表1",Student.class,fieldMap,uniqueFields);
        } catch (ExcelException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
