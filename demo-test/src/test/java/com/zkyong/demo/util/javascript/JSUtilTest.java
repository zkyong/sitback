package com.zkyong.demo.util.javascript;

import javax.script.ScriptException;

import com.zkyong.demo.javascript.JSUtil;
/**
 * JSUtil测试类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:29:43
 */
public class JSUtilTest {
    public static void main(String[] args) throws NoSuchMethodException, ScriptException {
        String str = "var a=3; var b=4;print (a+b);";
        JSUtil.invoke(str);
        System.out.println(JSUtil.invokeEncode("ABC"));
    }
}
