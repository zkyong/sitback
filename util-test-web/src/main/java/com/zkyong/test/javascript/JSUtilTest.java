package com.zkyong.test.javascript;

import javax.script.ScriptException;

import com.zkyong.util.javascript.JSUtil;

public class JSUtilTest {
    public static void main(String[] args) throws NoSuchMethodException, ScriptException {
        String str = "var a=3; var b=4;print (a+b);";
        JSUtil.invoke(str);
        System.out.println(JSUtil.invokeEncode("ABC"));
    }
}
