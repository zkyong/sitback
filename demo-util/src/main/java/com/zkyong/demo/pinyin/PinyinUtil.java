package com.zkyong.demo.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 拼音工具类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:19:44
 */
public class PinyinUtil {
    public static String getPinyinHeadChar(String chineseChar) {
        String convert = "";
        for (int j = 0; j < chineseChar.length(); j++) {
            char word = chineseChar.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }
}
