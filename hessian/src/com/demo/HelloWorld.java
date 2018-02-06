package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.hessian.server.HessianServlet;

@WebServlet("/HelloServlet") 
public class HelloWorld extends HessianServlet {

    private static final long serialVersionUID = -6756359352000268415L;

    private String message;

    @Override
    public void init() throws ServletException {
        // 执行必需的初始化
        message = "Hello World , Nect To Meet You ! Init Time: " + new Date();
        System.out.println("servlet初始化……");
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html");
        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.write("<h1>" + message + "</h1>");
        out.write("<h1>" + new Date() + "</h1>");
        destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void destroy() {
        System.out.println("servlet销毁！");
        super.destroy();
    }
}