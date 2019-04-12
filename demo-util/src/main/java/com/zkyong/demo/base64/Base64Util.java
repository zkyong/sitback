package com.zkyong.demo.base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Base64编码工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:55:18
 */
public class Base64Util {

    /**
     * 将文件转换为Base64编码的字符串
     * 
     * @param filePath 文件路径
     * @return String Base64字符串
     */
    public static String file2Base64(String filePath) {
        InputStream in = null;
        byte[] bytes = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(filePath);
            bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * 将Base64编码的字符串转换为文件
     * 
     * @param filePath 文件输出路径
     * @param data 文件Base64编码值
     * @return Boolean 是否处理成功
     */
    public static Boolean base642File(String filePath, String data) {
        // 数据为空
        if (data == null) {
            return false;
        }
        try {

            // 对字节数组Base64解码
            byte[] bytes = Base64.getDecoder().decode(data.getBytes());
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成新文件
            OutputStream out = new FileOutputStream(filePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}