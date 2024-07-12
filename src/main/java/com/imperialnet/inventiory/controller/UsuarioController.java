/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Rol;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.service.IRolService;
import com.imperialnet.inventiory.service.IUsuarioService;
import com.imperialnet.inventiory.util.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import java.lang.System.Logger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        if(userServ.verificarNombreUsisario(usuario.getNombreUsuario())){
            model.addAttribute("errorUser", "El usuario ya se encuentra registrado");
            model.addAttribute("user", usuario.getNombreUsuario());
            return "registrarUsuario"; 
        }
        
        
       // Guardar el usuario en la base de datos
        userServ.crearUduario(usuario);
         return "redirect:/verUsuarios";
    }

    @GetMapping("/menuUsuarios")
    public String menuUsuarios(HttpSession sesion, Model model) {
    // Obtener el rol del usuario desde la sesión o el modelo, según cómo lo hayas almacenado
    Integer rol = (Integer) sesion.getAttribute("rol");
    
    // Verificar si el rol es 1 (administrador)
    if (rol != null && rol == 1) {
        return "menuUsuarios"; // Redirigir al menú de usuarios
    } else {
        return "accesoDenegado"; // Redirigir al índice si el rol no es 1 o no está presente
    }
}
    
    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession sesion, Model model) throws NoSuchAlgorithmException {
        Optional<Usuario> user = userServ.buscarUsuarioPorUserName(usuario.getNombreUsuario());

        if (user.isPresent()) {
            if (PasswordUtils.verifyPassword(usuario.getClave(), user.get().getClave())){
                sesion.setAttribute("idUsuario", user.get().getId());
                int rol = user.get().getRol().getId();
                model.addAttribute("rol", rol);
                sesion.setAttribute("rol", rol);
                sesion.setAttribute("userNombre", user.get().getNombreUsuario());

                return "index"; // Devuelve la vista index directamente
            } else {
                model.addAttribute("error", "Las claves no coinciden");
                return "redirect:/?error=Clave+incorrecta."; // Redirige con el mensaje de error en la URL
            }
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "redirect:/?error=Usuario+inexistente"; // Redirige con el mensaje de error en la URL
        }
    }


    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession sesion){
        sesion.removeAttribute("idUsuario");
        sesion.removeAttribute("userNombre");
        sesion.removeAttribute("rol");
        sesion.invalidate();
        
        return "redirect:/?mensaje=Ha+finalizado+Correctamente";
    }
    
    @GetMapping("/actualizarUsuario")
    public String actualizarUsuario(@RequestParam(name = "id", required = false) Long id, Model model) {
        Usuario usu = userServ.obtenerUsuarioPorId(id);

        if (usu != null) {
            model.addAttribute("us", usu);
            return "actualizarUsuario";
        } else {
            return "verUsuarios";  // O cualquier otra página en caso de que no se encuentre el usuario
        }
    }

    
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(@ModelAttribute("us") Usuario usuario,
                                BindingResult result, 
                                Model model,
                                @RequestParam("rolId") long id,
                                @RequestParam("clave_repeat") String claveRepeat){
   if (!usuario.getClave().equals(claveRepeat)) {
        model.addAttribute("errorClave", "Las contraseñas no coinciden");
        return "actualizarUsuario";
    }
    if (result.hasErrors()) {
        // Si hay errores en el formulario, devolver a la misma página con el modelo actual.
        return "actualizarUsuario";
    }
    Rol role=rolServ.obtenerRolPorId(id);
    usuario.setRol(role);
    
    if(usuario.getClave().trim().isEmpty()){
     
         userServ.editarUsuarioSinClave(usuario.getId(), usuario);
        
    }else{
        userServ.editarUsuario(usuario.getId(), usuario);
    }
  
    return "redirect:/verUsuarios";
}

}
