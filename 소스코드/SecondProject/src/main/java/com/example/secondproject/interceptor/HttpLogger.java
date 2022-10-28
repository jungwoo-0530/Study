package com.example.secondproject.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;

public class HttpLogger implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headers = request.getHeaderNames();
        System.out.println("==================header================");
        while(headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + "=" + value);
        }
        System.out.println("=============header end================");
        System.out.println("==================body=================");
        InputStream a = request.getInputStream();
        BufferedReader dis = new BufferedReader(new InputStreamReader(a));
        String str = null;
        while ((str = dis.readLine()) != null) {
            System.out.println(new String(str.getBytes("ISO-8859-1"), "utf-8") + "/n");
            // euc-kr로 전송된 한글은 깨진다.
        }
        System.out.println("=============body end==============");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
