package com.sandrajavaschool.OnlineStore.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

/*
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/
    //metodo para registrar un controlador de vista y
    //personalizar la pagina de error 403 (De acceso denegado)

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error/error403").setViewName("error/error403");
    }
}
