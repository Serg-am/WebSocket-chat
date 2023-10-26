package com.example.websocketchat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class ViewConfig {
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(resolver.getTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}


