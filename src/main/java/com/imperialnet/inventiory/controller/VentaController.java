/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.service.IClienteService;
import com.imperialnet.inventiory.service.IProductoService;
import com.imperialnet.inventiory.service.IVentaService;
import com.imperialnet.inventiory.service.ProcutosSesionData;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jonii
 */
@Controller
public class VentaController {
    @Autowired
    IProductoService prodServ;
    @Autowired
    IClienteService cliServ;
    
    @Autowired
    IVentaService ventaServ;
    
    @Autowired
    ProcutosSesionData listado;
      

   

    @GetMapping("/registrarVenta")
    public String panelVenta(Model model, HttpSession sesion){
    List<Producto>productos=prodServ.getProductos();
    model.addAttribute("productos", productos);
    List<Cliente> clientes=cliServ.getClientes();
    model.addAttribute("clientes", clientes);
   
    
    return "registrarVenta";
    }
    
    
    @GetMapping("/seleccionarCliente")
    public String seleccionarCliente(HttpSession sesion, @RequestParam ("dniCliente")String dni){
        Cliente cliente= cliServ.findByDni(dni);
        sesion.setAttribute("cliente", cliente);
        return "redirect:/registrarVenta";
    }
  
   
    @PostMapping("/agregarProducto")
    public String agregarProducto(@RequestParam("idProducto") Long idProducto, 
                                  Model model,
                                  HttpSession sesion) {
        
        Producto productoSeleccionado = prodServ.obtenerProductoPorId(idProducto);

        
     
       
        
        listado.agregarProducto(productoSeleccionado);
        sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());
        sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
        
        
        
        return "redirect:/registrarVenta";
    }
     @PostMapping("/eliminarProducto")
     public String eliminarProducto(@RequestParam ("idProd") Long idProd,
                                    HttpSession sesion){
          listado.getProductosSeleccionados().removeIf(producto -> producto.getId().equals(idProd));
         
          sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());
          sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
         return "redirect:/registrarVenta";
     }
    
    
    
    
    
    
}
