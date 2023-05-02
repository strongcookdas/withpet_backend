package com.ajou_nice.with_pet.configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://ec2-13-125-242-183.ap-northeast-2.compute.amazonaws.com")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
}
