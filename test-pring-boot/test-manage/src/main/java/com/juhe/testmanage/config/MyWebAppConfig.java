package com.juhe.testmanage.config;

import com.juhe.testmanage.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("juhe/toy/allUploadImg/**").addResourceLocations("file:Z:/testJava/img/");
        // 前面(addResourceHandler)就是前段请求接口的路径, 后面(addResourceLocations)就是服务器的磁盘路径
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/juhe/**").excludePathPatterns("/juhe/login/loginIn","/juhe/toy/allUploadImg/*.jpg");
    }
}
