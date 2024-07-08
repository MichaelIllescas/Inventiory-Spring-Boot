/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Rol;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.service.IRolService;
import com.imperialnet.inventiory.service.IUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jonii
 */
@Controller
public class UsuarioController {
    
    @Autowired
    IUsuarioService userServ;
    
    @Autowired
    IRolService rolServ;
            
    
    @GetMapping("/verUsuarios")
    public String verUsuarios(Model model){
        List<Usuario>usuarios=userServ.getUsuarios();
        model.addAttribute("listadoUsuarios", usuarios);
        return "verUsuarios";
    }
    
    @GetMapping("/registrarUsuario")
    public String registrarUsuario(){
        return "registrarUsuario";
    }
    
    @PostMapping("/registrarUsuario")
    public String crearUsuario(@ModelAttribute("usuario") Usuario usuario,
                           @RequestParam("rolId") Long rolId,
                           Model model) {
    // Obtener el rol desde el servicio de roles
    Rol rol = rolServ.obtenerRolPorId(rolId);
    
    // Asignar el rol al usuario
    usuario.setRol(rol);
    
    // Validar que las contraseñas coincidan
    if (!usuario.getClave().equals(usuario.getClave_repeat())) {
        model.addAttribute("errorClave", "Las contraseñas no coinciden");
        return "registrarUsuario"; // Retorna al formulario con el mensaje de error
    }
    
    // Guardar el usuario en la base de datos
    userServ.crearUduario(usuario);
    
    return "redirect:/verUsuarios";
}


    @GetMapping("/menuUsuarios")
    public String menuUsuarios(){
        return "menuUsuarios";
    }
}
