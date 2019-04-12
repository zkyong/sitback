package com.zkyong.demo.base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @title Base64Util
 * @description Base64编码工具类
 * @author zhangkaiyong
 * @date 2018年9月20日 14:15:24
 */
@SuppressWarnings("restriction")
public class Base64Util {

    /**
     * @title file2Str
     * @description 将文件转换为Base64编码的字符串
     * @param path 文件路径
     * @return String Base64编码的字符串
     * @date 2018年9月20日 14:15:24
     */
    public static String file2Str(String path) {
        InputStream in = null;
        byte[] bytes = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(path);
            bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    /**
     * @title str2File
     * @description 将Base64编码的字符串转换为文件
     * @param path 文件路径
     * @return Boolean 是否转换成功
     * @date 2018年9月20日 14:15:24
     */
    public static Boolean str2File(String path, String data) {
        // 数据为空
        if (data == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 对字节数组Base64解码
            byte[] bytes = decoder.decodeBuffer(data);
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成新文件
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}