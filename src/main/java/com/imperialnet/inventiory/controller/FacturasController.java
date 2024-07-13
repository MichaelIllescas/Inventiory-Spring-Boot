
package com.imperialnet.inventiory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author jonii
 */
@Controller
public class FacturasController {
    
    @GetMapping("/menuFacturas")
    public String menuFacturas(){
        return "menuFacturas";
    }
}
