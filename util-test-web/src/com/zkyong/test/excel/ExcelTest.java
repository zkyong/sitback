package com.zkyong.test.excel;

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

import com.zkyong.exception.excel.ExcelException;
import com.zkyong.util.excel.ExcelUitl;

/**
 * ExcelUtil��excel���������������<BR>
 * @author zkyong<BR>
 * @date 2017��9��28�� 15:34:16<BR>
 * @version 1.0.0<BR>
 */
public class ExcelTest {

    public static void main(String[] args) {
        String fullPath = "C:/Users/zkyong/Desktop/ѧ���ɼ���.xls " ; //�����excel�ļ���   
        listToExcelTest(fullPath);
        System.out.println(","+excelToListTest(fullPath));
        //String str = "college";
        //System.out.println(str.split("\\."));
    }
    
    /**
     * �������Excel��ָ��·��<BR>
     * @param fullPath ȫ·��<BR>
     */
    private static void listToExcelTest(String fullPath) {
        Student student = null;
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 198 ; i++) {
            student = new Student();
            student.setId("ID000"+(i+1));
            student.setName("ѧ��"+(i+1));
            student.setSex(new BigDecimal(Math.random()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
            student.setScore(new BigDecimal(Math.random()*50).intValue()+50);
            student.setAge(new BigDecimal(Math.random()*3).intValue() + 15);
            College college = new College();
            college.setCollegeId(new BigDecimal(Math.random()*5).intValue());
            college.setCollegeName("ѧԺ"+college.getCollegeId());
            School school = new School();
            school.setSchoolId("SL2010100");
            school.setSchoolName("XXXX��ѧ");
            college.setSchool(school);
            student.setCollege(college);
            list.add(student);
            System.out.println(student);
        }
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();;
        fieldMap.put("id", "ѧ��");
        fieldMap.put("name", "����");
        fieldMap.put("sex", "�Ա�");
        fieldMap.put("age", "����");
        fieldMap.put("score", "����");
        fieldMap.put("college.collegeId", "ѧԺ���");
        fieldMap.put("college.collegeName", "ѧԺ����");
        fieldMap.put("college.school.schoolId", "ѧУ���");
        fieldMap.put("college.school.schoolName", "ѧУ����");
        OutputStream os = null;
        try {
            os = new  FileOutputStream(fullPath);
            ExcelUitl.listToExcel(list, fieldMap, "ѧ���ɼ���", 200, os);
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
     * ���Դ�Excel��ȡ��List<T><BR>
     * @param fullPath ȫ·��<BR>
     */
    private static List<Student> excelToListTest(String fullPath) {
        InputStream in = null;
        List<Student> list = null;
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("ѧ��", "id");
        fieldMap.put("����", "name");
        fieldMap.put("�Ա�", "sex");
        fieldMap.put("����", "age");
        fieldMap.put("����", "score");
        fieldMap.put("ѧԺ���", "college.collegeId");
        fieldMap.put("ѧԺ����", "college.collegeName");
        fieldMap.put("ѧУ���","college.school.schoolId");
        fieldMap.put("ѧУ����","college.school.schoolName");
        String[] uniqueFields = {"ѧ��"};
        try {
            in = new FileInputStream(fullPath);
            list = ExcelUitl.excelToList(in,"ѧ���ɼ���1",Student.class,fieldMap,uniqueFields);
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
