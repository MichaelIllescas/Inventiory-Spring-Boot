/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;



import com.imperialnet.inventiory.service.IClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author jonii
 */
@Controller
public class ClienteController {
    
    @Autowired
    IClienteService cliServ;
    
 @GetMapping ("/registrarCliente")
    public String IngresoApartado(){
        
        return "registrarCliente";
    }
    
    @PostMapping("/registrarCliente")
    public String rgistrarClnte( @ModelAttribute Cliente cliente){
        cliServ.crearCliente(cliente);
        
        return "redirect:/verClientes";
    }
    
    @GetMapping("/verClientes")
    public String listarProductos(Model model) {
        List<Cliente> listaClientes = cliServ.getClientes();
        model.addAttribute("listadoClientes", listaClientes);
        return "verClientes"; 
    }
    
    @GetMapping("/actualizarCliente")
    public String ingresoActualizarCliente(Model model, @RequestParam("id") Long id) {
        Cliente cliente = cliServ.obtenerClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "actualizarCliente"; // Nombre de la vista para editar cliente
}
    
    @PostMapping("/actualizarCliente")  
    public String actualizarCliente( Cliente cliente) {
        cliServ.editarCliente(cliente);
        return "redirect:/verClientes"; // Redirige al listado de clientes después de editar
}
   
    @GetMapping("/menuClientes")
    public String menuClientes(){
        return "menuClientes";
    }
    
    
}
