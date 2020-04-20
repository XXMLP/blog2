package com.xxmlp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory=new MultipartConfigFactory();
		//最大上传文件
		factory.setMaxFileSize("100MB");
		//最大请求文件
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}
}
