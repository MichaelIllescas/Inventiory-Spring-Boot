package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Rol;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.service.IRolService;
import com.imperialnet.inventiory.service.IUsuarioService;
import com.imperialnet.inventiory.util.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    IUsuarioService userServ;

    @Autowired
    IRolService rolServ;

    // Muestra la lista de usuarios
    @GetMapping("/verUsuarios")
    public String verUsuarios(Model model)
    {
        List<Usuario> usuarios = userServ.getUsuarios();
        model.addAttribute("listadoUsuarios", usuarios);
        return "verUsuarios"; // Retorna la vista verUsuarios
    }

    // Muestra el formulario para registrar un nuevo usuario
    @GetMapping("/registrarUsuario")
    public String registrarUsuario() 
    {
        return "registrarUsuario"; // Retorna la vista registrarUsuario
    }

    // Procesa el formulario para crear un nuevo usuario
    @PostMapping("/registrarUsuario")
    public String crearUsuario(@ModelAttribute("usuario") Usuario usuario,
                               @RequestParam("rolId") Long rolId,
                               Model model) 
    {
        // Obtener el rol desde el servicio de roles
        Rol rol = rolServ.obtenerRolPorId(rolId);
        // Asignar el rol al usuario
        usuario.setRol(rol);

        // Validar que las contraseñas coincidan
        if (!usuario.getClave().equals(usuario.getClave_repeat())) {
            model.addAttribute("errorClave", "Las contraseñas no coinciden");
            return "registrarUsuario"; // Retorna al formulario con el mensaje de error
        }

        // Verificar si el nombre de usuario ya está registrado
        if (userServ.verificarNombreUsisario(usuario.getNombreUsuario())) {
            model.addAttribute("errorUser", "El usuario ya se encuentra registrado");
            model.addAttribute("user", usuario.getNombreUsuario());
            return "registrarUsuario"; // Retorna al formulario con el mensaje de error
        }

        // Guardar el usuario en la base de datos
        userServ.crearUduario(usuario);
        return "redirect:/verUsuarios"; // Redirige a la lista de usuarios después de crear uno nuevo
    }

    // Muestra el menú de usuarios, verifica el rol del usuario desde la sesión
    @GetMapping("/menuUsuarios")
    public String menuUsuarios(HttpSession sesion)
    {
        Integer rol = (Integer) sesion.getAttribute("rol");

        // Verificar si el rol es 1 (administrador)
        if (rol != null && rol == 1) {
            return "menuUsuarios"; // Redirige al menú de usuarios
        } else {
            return "accesoDenegado"; // Redirige a la página de acceso denegado si el rol no es 1 o no está presente
        }
    }

    // Procesa el inicio de sesión del usuario
    @PostMapping("/acceder")
    public String acceder(Usuario usuario,
                          HttpSession sesion,
                          Model model) throws NoSuchAlgorithmException 
    {
        // Buscar el usuario por nombre de usuario
        Optional<Usuario> user = userServ.buscarUsuarioPorUserName(usuario.getNombreUsuario());

        if (user.isPresent()) {
            // Verificar si el usuario está activo
            if (user.get().getActivo() != 1) {
                model.addAttribute("error", "Usuario Inhabilitado");
                return "redirect:/?error=Usuario+Inhabilitado."; // Redirige con el mensaje de error en la URL
            }
            // Verificar si la contraseña coincide
            if (PasswordUtils.verifyPassword(usuario.getClave(), user.get().getClave())) {
                sesion.setAttribute("idUsuario", user.get().getId());
                sesion.setAttribute("rol", user.get().getRol().getId());
                sesion.setAttribute("userNombre", user.get().getNombreUsuario());
                return "index"; // Redirige al index después de iniciar sesión
            } else {
                model.addAttribute("error", "Las claves no coinciden");
                return "redirect:/?error=Clave+incorrecta."; // Redirige con el mensaje de error en la URL
            }
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "redirect:/?error=Usuario+inexistente"; // Redirige con el mensaje de error en la URL
        }
    }

    // Cierra la sesión del usuario
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession sesion)
    {
        sesion.invalidate(); // Invalida la sesión eliminando todos los atributos
        return "redirect:/?mensaje=Ha+finalizado+Correctamente"; // Redirige con el mensaje de éxito en la URL
    }

    // Muestra el formulario para actualizar un usuario específico
    @GetMapping("/actualizarUsuario")
    public String actualizarUsuario(@RequestParam(name = "id", required = false) Long id,
                                    Model model)
    {
        // Obtener el usuario por su ID
        Usuario usu = userServ.obtenerUsuarioPorId(id);

        if (usu != null) {
            model.addAttribute("us", usu);
            return "actualizarUsuario"; // Retorna la vista actualizarUsuario con el usuario a actualizar
        } else {
            return "verUsuarios"; // Retorna la lista de usuarios si no se encuentra el usuario
        }
    }

    // Procesa el formulario para actualizar un usuario
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(@ModelAttribute("us") Usuario usuario,
                                    BindingResult result,
                                    Model model,
                                    @RequestParam("rolId") long id,
                                    @RequestParam("clave_repeat") String claveRepeat)
    {
        // Validar que las contraseñas coincidan
        if (!usuario.getClave().equals(claveRepeat)) {
            model.addAttribute("errorClave", "Las contraseñas no coinciden");
            return "actualizarUsuario"; // Retorna al formulario con el mensaje de error
        }
        // Validar errores en el formulario
        if (result.hasErrors()) {
            return "actualizarUsuario"; // Retorna al formulario con los errores encontrados
        }
        // Obtener el rol desde el servicio de roles
        Rol role = rolServ.obtenerRolPorId(id);
        usuario.setRol(role);

        // Editar el usuario en la base de datos
        if (usuario.getClave().trim().isEmpty()) {
            userServ.editarUsuarioSinClave(usuario.getId(), usuario); // Edita el usuario sin cambiar la clave
        } else {
            userServ.editarUsuario(usuario.getId(), usuario); // Edita el usuario con nueva clave
        }

        return "redirect:/verUsuarios"; // Redirige a la lista de usuarios después de actualizar
    }
    @PostMapping("/anularUsuario")
    public String anularUsuario(@RequestParam ("id")Long id)
    {
      Usuario usu= userServ.obtenerUsuarioPorId(id);
      userServ.anularUsuario(usu);
    return "redirect:/verUsuarios";
    }
}
