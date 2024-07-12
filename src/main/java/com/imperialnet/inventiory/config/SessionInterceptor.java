/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author jonii
 */
@Component
public class SessionInterceptor implements HandlerInterceptor{
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                             Object handler   )throws Exception{
        if(request.getSession().getAttribute("idUsuario")==null){
            response.sendRedirect("/");
            return false;
        }   return true;
    }
}
