package com.zkyong.demo.util.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zkyong.demo.http.HttpUtil;

/**
 * Http调用工具测试类
 * 
 * @author zkyong
 * @date 2019-07-02 15:23:10
 * @version v0.1
 */
public class HttpUtilTest {

    @Test
    public void test01() throws Exception {
        String url = "http://localhost:8080/hello";
        String json = "{\"hello\":\"wellcome\", \"name\":\"zhangsan\"}";
        int socketTimeout = 120 * 1000;
        int connectTimeout = 120 * 1000;
        Map<String, String> params = new HashMap<>();
        params.put("p1", "param01");
        params.put("p2", "param02");
        params.put("p3", "param03");
        params.put("p4", "param04");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String response = HttpUtil.sendPost(url, socketTimeout, connectTimeout, headers, params, json);
        System.out.println("==================================================");
        System.out.println(response);
    }
}
