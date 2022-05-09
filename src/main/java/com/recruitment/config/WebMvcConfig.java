package com.recruitment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author： wzz
 * @date： 2021-03-18 14:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    MyInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(securityInterceptor);

//        拦截url
//        interceptor.addPathPatterns("/**");
        interceptor.addPathPatterns("/home/**");
        interceptor.addPathPatterns("/excelUpload/**");
        interceptor.addPathPatterns("/plan/**");
        interceptor.addPathPatterns("/user/**");

//        去除error和login
        interceptor.excludePathPatterns("/");

    }

    @Value("${path.savePath")
    String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + path + "/static");
    }

}
