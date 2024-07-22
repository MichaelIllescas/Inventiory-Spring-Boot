package com.imperialnet.inventiory.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception 
    {
        if (request.getSession().getAttribute("idUsuario") == null) {
            // Set the session attribute for the session expiration message
            request.getSession().setAttribute("sessionExpired", "La sesión ha expirado. Por favor, inicia sesión nuevamente.");
            
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
