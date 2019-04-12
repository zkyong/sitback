package com.zkyong.demo.string;

/**
 * 字符串处理工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:20:06
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     * 
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}
