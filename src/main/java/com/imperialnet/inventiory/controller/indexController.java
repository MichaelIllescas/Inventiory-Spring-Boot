
package com.imperialnet.inventiory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author jonii
 */
@Controller 
public class IndexController {
    
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    
    @GetMapping("/")
    public String inicio(){
        return "login";
    }
    @GetMapping("/menuProductos")
    public String menuProductos(){
        return "menuProductos";
    }
    @GetMapping("/crearProductos")
    public String crearProductos(){
        return "crearProductos";
    }
    @GetMapping("/cerrarSesion")
    public String cerrarSesion(){
        return "login";
    }
     @GetMapping("/actualizarProductos")
    public String actualizarProductos(){
        return "actualizarProductos";
    }
    
     @GetMapping("/inicioSesion")
    public String inicioSesion(){
        return "index";
    }

}
