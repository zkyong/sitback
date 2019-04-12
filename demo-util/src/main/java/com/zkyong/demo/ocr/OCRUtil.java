package com.zkyong.demo.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * OCR处理工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:19:27
 */
public class OCRUtil {

    // OCR的都是命令方式调用
    public static String getImgText(String imgPath) {
        String result = "";
        BufferedReader br = null;
        // 中文识别 -l chi_sim -psm 7 nobatch
        String ocrLangData = "-l chi_sim";// 识别语言
        String outPath = imgPath.substring(0, imgPath.lastIndexOf("."));
        File file = new File(outPath + ".txt");
        try {
            // dos执行
            Runtime runtime = Runtime.getRuntime();
            String command = "E:\\OCR\\Tesseract-OCR\\tesseract " + imgPath + " " + outPath + " "
                             + ocrLangData;
            System.out.println("command: " + command);
            Process ps = runtime.exec(command);
            ps.waitFor();
            // 读取文件
            if (!file.exists()) {
                file.createNewFile();
            }
            br = new BufferedReader(new FileReader(file));
            String temp = "";
            StringBuffer sb = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            // 文字识别结果
            result = sb.toString();
        } catch (Exception e) {
            System.out.println("识别图片异常！");
            e.printStackTrace();
        } finally {
            try {
                br.close();
                // 读取完后删除文件
                //				file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 处理一个文件夹中的所有验证码
    public static void getImgTxtList(String filepath) {
        File file = new File(filepath);
        File[] fileList = file.listFiles();
        for (File f : fileList) {
            String imgpath = f.getAbsolutePath();
            if (imgpath.endsWith("jpg") || imgpath.endsWith("png") || imgpath.endsWith("gif")) {
                String resultTxt = getImgText(imgpath);
                System.out.println("result: " + resultTxt);
            }
        }
        //		String path = "C:\\Users\\zkyong\\Desktop\\Tpic\\2.gif";
        //		System.out.println("path:" + path);
        //		File file = new File(path);
        //		String imgpath = file.getAbsolutePath();
        //		System.out.println("imgpath:" + imgpath);
        //		String resultTxt = getImgText(imgpath);
        //		System.out.println("result: " + resultTxt);
    }
}