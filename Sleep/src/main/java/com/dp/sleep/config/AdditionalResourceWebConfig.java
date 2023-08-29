package com.dp.sleep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class AdditionalResourceWebConfig implements WebMvcConfigurer {

    @Value("${mp.path}")
    private String mpPath;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/mp/**").addResourceLocations("file:" + mpPath + "/");
    }
}
