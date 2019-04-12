package com.zkyong.demo.util.base64;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zkyong.demo.base64.Base64Util;

public class Base64UtilTest {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

        String path = "D:\\tupian";
        String str = Base64Util.file2Str(path + "\\new.jpg");
        System.out.println(str);
        str = "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";
        Base64Util.str2File(path + "\\" + sdf.format(new Date()) + ".jpg", str);
    }
}
