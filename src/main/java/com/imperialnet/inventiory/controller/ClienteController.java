package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.service.IClienteService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    @Autowired
    private IClienteService cliServ; 

    /*
     * Método para mostrar la página de registro de clientes.
     */
    @GetMapping("/registrarCliente")
    public String mostrarFormularioRegistroCliente() 
    {
        return "registrarCliente"; // Devuelve la vista de registro de clientes
    }

    /*
     * Método para procesar el formulario de registro de clientes.
     */
    @PostMapping("/registrarCliente")
    public String registrarCliente(@ModelAttribute Cliente cliente,
                                    HttpSession sesion,
                                    Model model)
    {
        // Verificar si el cliente ya está registrado por su DNI
        Cliente clienteExistente = cliServ.findByDni(cliente.getDni(), (Long)sesion.getAttribute("idUsuario"));

        if (clienteExistente != null) {
            sesion.setAttribute("errorReg", "El cliente con DNI " + cliente.getDni() + " ya está registrado.");
            return "registrarCliente"; // Renderiza nuevamente el formulario de registro con el mensaje de error
        }

        // Si no hay cliente registrado con ese DNI, procede a crearlo
        cliServ.crearCliente(cliente, sesion);
        sesion.removeAttribute("errorReg");

        return "redirect:/verClientes"; // Redirige al listado de clientes después de registrar
    }

    /*
     * Método para mostrar el listado de clientes asociados al usuario actual.
     */
    @GetMapping("/verClientes")
    public String verClientes(Model model,
                              HttpSession sesion) 
    {
        Long idUsuario = (Long) sesion.getAttribute("idUsuario"); // Obtiene el ID del usuario de la sesión
        List<Cliente> listaClientes = cliServ.obtenerClientesPorUsuario(idUsuario); // Obtiene la lista de clientes del servicio
        model.addAttribute("listadoClientes", listaClientes); // Agrega la lista de clientes al modelo
        return "verClientes"; // Devuelve la vista de listado de clientes
    }

    /*
     * Método para mostrar el formulario de actualización de cliente.
     */
    @GetMapping("/actualizarCliente")
    public String mostrarFormularioActualizarCliente(Model model,
                                                     @RequestParam("id") Long id) 
    {
        Cliente cliente = cliServ.obtenerClientePorId(id); // Obtiene el cliente por su ID
        model.addAttribute("cliente", cliente); // Agrega el cliente al modelo
        return "actualizarCliente"; // Devuelve la vista de actualización de cliente
    }

    /*
     * Método para procesar el formulario de actualización de cliente.
     */
    @PostMapping("/actualizarCliente")
    public String actualizarCliente(@ModelAttribute Cliente cliente)
    {
        cliServ.editarCliente(cliente); // Llama al servicio para editar el cliente
        return "redirect:/verClientes"; // Redirige al listado de clientes después de actualizar
    }

    /*
     * Método para mostrar el menú de clientes.
     */
    @GetMapping("/menuClientes")
    public String mostrarMenuClientes()
    {
        return "menuClientes"; // Devuelve la vista del menú de clientes
    }
}
