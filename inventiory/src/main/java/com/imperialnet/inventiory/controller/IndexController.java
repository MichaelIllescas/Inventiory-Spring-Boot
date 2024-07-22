
package com.imperialnet.inventiory.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *
 * @author jonii
 */
@Controller 
public class IndexController {
    
    @GetMapping("/index")
    public String index()
    {
        return "index";
    }
 
   @GetMapping("/")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
       
        return "login"; // Ensure you have a login.html Thymeleaf template
    }
    
    @GetMapping("/menuProductos")
    public String menuProductos()
    {
        return "menuProductos";
    }
    
    @GetMapping("/crearProductos")
    public String crearProductos()
    {
        return "crearProductos";
    }
    @GetMapping("/cerrarSesion")
    public String cerrarSesion()
    {
        return "login";
    }
    @GetMapping("/actualizarProductos")
    public String actualizarProductos()
    {
        return "actualizarProductos";
    }
    
    @GetMapping("/inicioSesion")
    public String inicioSesion()
    {
        return "index";
    }

}
