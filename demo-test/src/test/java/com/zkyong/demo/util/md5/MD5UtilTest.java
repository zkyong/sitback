package com.zkyong.demo.util.md5;

import com.zkyong.demo.md5.MD5Util;

public class MD5UtilTest {

    // 测试主函数
    public static void main(String args[]) {
        String s = new String("tangfuqiang");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + MD5Util.encode(s));
        System.out.println("加密的：" + MD5Util.decode(s));
        System.out.println("解密的：" + MD5Util.decode(MD5Util.decode(s)));
    }
}
