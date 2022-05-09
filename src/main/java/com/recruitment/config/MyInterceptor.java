package com.recruitment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author： wzz
 * @date： 2021-04-01 11:17
 */

@Component
public class MyInterceptor implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(MyInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //用户进入的页面
        String url = request.getRequestURL().toString();
        log.info("进入地址---"+url);
        //将用户信息存入session
        HttpSession session = request.getSession();
        //是否登录
        log.info("登录状态---"+url.endsWith("login"));
        System.out.println(session.getAttribute("admin"));
        if (session.getAttribute("admin") != null ) {   //session不为空登录成功
            return true;
        }else if ("/admin/login".equals(url)){
            response.sendRedirect("/");
            return false;
        }else {         //session为空跳到登录页面
            response.sendRedirect("/");
//            returnJson(response);
            return false;
        }

    }

    /**
     * 返回json，前后端分离
     * @param response
     */
    private void returnJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
//            writer.print("{\"message\":\"请先登录系统\"}");
            writer.print("请先登录系统");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null) {
                writer.close();
            }
        }
    }

}
