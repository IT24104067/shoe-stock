package com.example.shoestock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path to the frontend directory
        String frontendPath = Paths.get("frontend").toAbsolutePath().toString();

        // Serve static files from frontend directory
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + frontendPath + "/")
                .addResourceLocations("classpath:/static/");

        // Explicitly handle assets
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:" + frontendPath + "/assets/");

        // Explicitly handle views
        registry.addResourceHandler("/views/**")
                .addResourceLocations("file:" + frontendPath + "/views/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Forward root URL to index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}

