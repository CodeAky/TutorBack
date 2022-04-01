package com.example.tutorback.utils;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Aky
 * @version 1.0
 * @date 2022/3/30 15:55
 */
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("getImage/**").addResourceLocations("file:E:/JAVA/Project/cache");
    }
}
