/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.service.IVentaService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jonii
 */
@Controller
public class InformesController {

    @Autowired
    IVentaService ventaService;

    @GetMapping("/menuInformes")
    public String informesMenu() {
        return "menuInformes";
    }

    @GetMapping("/informeVentasPanel")

    public String panelInformeVentas() {

        return "informeVentas";
    }

    @GetMapping("/informeVentas")
    public String mostrarInformeVentas(@RequestParam("mes") int mesSeleccionado, Model model, HttpSession sesion) {
        
  model.addAttribute("mesSeleccionado", mesSeleccionado);
// Obtener las ventas para el mes seleccionado
        List<Venta> ventas = ventaService.obtenerVentasPorMes(ventaService.findByUsuarioId((Long) sesion.getAttribute("idUsuario")), mesSeleccionado);

            // Validar si hay ventas para el mes seleccionado
    if (ventas.isEmpty()) {
        // Agregar un mensaje de error al modelo y retornar a la vista
        model.addAttribute("error", "No hay ventas registradas para el mes seleccionado.");
        return "informeVentas";
    }

        // Obtener el cliente que más compró en el mes y el monto total de sus compras
        Cliente clienteTop = ventaService.obtenerClienteQueMasComproEnMes(mesSeleccionado, (Long) sesion.getAttribute("idUsuario"));
        
          // Validar si se encontró un cliente que más compró en el mes
    if (clienteTop == null) {
        // Agregar un mensaje de error al modelo y retornar a la vista
        model.addAttribute("error", "No se encontró cliente que más compró en el mes seleccionado.");
        return "informeVentas";
    }
        
        float montoTotal = ventaService.obtenerMontoTotalComprasClienteEnMes(clienteTop.getId(), mesSeleccionado, (Long) sesion.getAttribute("idUsuario"));

        //obtener el monto total de ventas en el mes
        
        float totalVentasMes= ventaService.obtenerMontoTotalComprasEnMes(ventas);
        
       
        // Agregar las ventas, el cliente top y el monto total al modelo para la vista
         model.addAttribute("mesSeleccionado", mesSeleccionado);
        model.addAttribute("ventas", ventas);
        model.addAttribute("clienteTop", clienteTop);
        model.addAttribute("montoTotal", montoTotal);
        model.addAttribute("totalVentasMes", totalVentasMes);
      
        // Retornar el nombre de la vista Thymeleaf que mostrará los resultados
        return "informeVentas";
    }

    @GetMapping("/informeProductos")
    public String informePrductos() {
        return "informeProductos";
    }

    @GetMapping("ventasPorMes")
    public String obtenerVentasMes() {

        return "informeVentas";
    }

    @GetMapping("ventas")
    public String obtenerVentas() {

        return "informeVentas";
    }
    
        @GetMapping("/informeProductosMasVendidos")
    public String informeProductosMasVendidos(@RequestParam(value = "mes", required = false) Integer mes, Model model, HttpSession sesion) {
        Long usuarioId = (Long) sesion.getAttribute("idUsuario");

        if (mes == null) {
            // Si no se ha seleccionado un mes, redirigir a la página principal del informe de productos
            return "redirect:/informeProductos";
        }

        List<Object[]> productosMasVendidos = ventaService.obtenerTopProductosVendidos(mes, usuarioId);

        if (productosMasVendidos.isEmpty()) {
            model.addAttribute("mensaje", "No hay productos vendidos en el mes seleccionado.");
        } else {
            model.addAttribute("productos", productosMasVendidos);
        }

        // Agregar el mes seleccionado al modelo para que pueda ser utilizado en la vista
        model.addAttribute("mesSeleccionado", mes);

        return "informeProductos";
    }

}
