package com.zkyong.demo.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;

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
