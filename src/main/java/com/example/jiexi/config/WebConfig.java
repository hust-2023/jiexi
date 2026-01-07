package com.example.jiexi.config;

import com.example.jiexi.security.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/task/**", "/api/report/**", "/api/user/profile")  // profile 也拦截
                .excludePathPatterns("/api/user/register", "/api/user/login");           // 登录/注册不拦截
    }

}
