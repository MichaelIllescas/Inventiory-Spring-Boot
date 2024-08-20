/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.service.IVentaService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

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
    public String informesMenu() 
    {
        return "menuInformes";
    }

    @GetMapping("/informeVentasPanel")

    public String panelInformeVentas() 
    {

        return "informeVentas";
    }

    @GetMapping("/informeVentas")
    public String mostrarInformeVentas(@RequestParam("mes") int mesSeleccionado,
                                        @RequestParam("anio") int anio,
                                        Model model, 
                                        HttpSession sesion)
    {

        model.addAttribute("mesSeleccionado", mesSeleccionado);
        model.addAttribute("anio", anio);

       
    
        // Obtener las ventas para el mes seleccionado
      
        
        List<Venta> ventas = ventaService.findVentasByUsuarioAndFecha((Long) sesion.getAttribute("idUsuario"),mesSeleccionado,anio);

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
        float totalVentasMes = ventaService.obtenerMontoTotalComprasEnMes(ventas);

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
    public String informePrductos()
    {
        return "informeProductos";
    }

    @GetMapping("ventasPorMes")
    public String obtenerVentasMes()
    {

        return "informeVentas";
    }

    @GetMapping("ventas")
    public String obtenerVentas() 
    {

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

    @GetMapping("/ventasDiarias")
    public String ventasDiarias()
    {
        return "ventasDiarias";
    }

    @GetMapping("/informeDiarioVentas")
    public String informeVentasDiarias(HttpSession sesion,
            @RequestParam(value = "dia", defaultValue = "-1") int dia,
            @RequestParam(value = "mes", defaultValue = "-1") int mes,
            @RequestParam(value = "anio", defaultValue = "-1") int anio,
            Model model) 
    {

        model.addAttribute("mesSeleccionado", mes);
        model.addAttribute("diaSeleccionado", dia);
        model.addAttribute("anioSeleccionado", anio);

        // Validaciones
        if (dia < 1 || dia > 31) {
            model.addAttribute("error", "El día debe estar entre 1 y 31.");
            return "ventasDiarias";
        }
        if (mes < 1 || mes > 12) {
            model.addAttribute("error", "El mes debe estar entre 1 y 12.");
            return "ventasDiarias";
        }
        if (anio < 1900 || anio > 2100) {
            model.addAttribute("error", "El año debe estar entre 1900 y 2100.");
            return "ventasDiarias";
        }

        List<Venta> ventas = ventaService.filterVentasByFecha(
                (Long) sesion.getAttribute("idUsuario"),
                dia,
                mes,
                anio
        );
        model.addAttribute("ventas", ventas);
        return "ventasDiarias";
    }
}
