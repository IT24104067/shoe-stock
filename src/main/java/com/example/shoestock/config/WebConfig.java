package com.example.shoestock.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path to the frontend directory (relative to working dir)
        String frontendPath = Paths.get("").toAbsolutePath().toString() + "/frontend";

        // Serve static files: try classpath first, then fallback to file://
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("file:" + frontendPath + "/");

        // Explicitly handle assets
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .addResourceLocations("file:" + frontendPath + "/assets/");

        // Explicitly handle views
        registry.addResourceHandler("/views/**")
                .addResourceLocations("classpath:/static/views/")
                .addResourceLocations("file:" + frontendPath + "/views/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Forward root URL to index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}

