package com.xxmlp.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");

        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/registers")
                .excludePathPatterns("/user/login");
    }
}
