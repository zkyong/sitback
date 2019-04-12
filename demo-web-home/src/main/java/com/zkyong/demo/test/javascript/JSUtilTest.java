package com.zkyong.demo.test.javascript;

import javax.script.ScriptException;

import com.zkyong.demo.util.javascript.JSUtil;

public class JSUtilTest {
    public static void main(String[] args) throws NoSuchMethodException, ScriptException {
        String str = "var a=3; var b=4;print (a+b);";
        JSUtil.invoke(str);
        System.out.println(JSUtil.invokeEncode("ABC"));
    }
}
