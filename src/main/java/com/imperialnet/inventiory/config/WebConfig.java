/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author jonii
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Autowired
    SessionInterceptor sessionInterceptor;
    
  @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(sessionInterceptor)
                .excludePathPatterns("/","/acceder", "/cerrar", "/css/**", "/js/**", "/img/**")
                .addPathPatterns("/**");
    }}