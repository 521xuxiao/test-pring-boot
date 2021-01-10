package com.juhe.testmanage.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("user_key")==null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null ;
                JSONObject res = new JSONObject();
                res.put("login","false");
                out = response.getWriter();
                out.append(res.toString());
                return false;
            }else {
                System.err.println("28: 你已经进入拦截器");
                session.setMaxInactiveInterval(60*60);  // 以秒为单位  60*60为一个小时
//                if("1".equals(request.getParameter("remote"))) {
//                    DataSourceContext.setDataSource("secondDataSource");
//                }else {
//                    DataSourceContext.setDataSource("firstDataSource");
//                }

                //DataSourceContext.setDataSource("firstDataSource");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
