package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private IProductoService prodServ;

    @PostMapping("/producto/crear")
    public String crearProducto(Producto producto) {
        prodServ.crearProducto(producto);
        return "redirect:/verProductos"; // Redirige a la página de listado de productos después de la creación
    }

    @GetMapping("/verProductos")
    public String listarProductos(Model model) {
        List<Producto> listaProductos = prodServ.getProductos();
        model.addAttribute("listadoProductos", listaProductos);
        return "verProductos"; 
    }

    @PostMapping("/producto/actualizar/{id}")
    public String actualizarProducto(@PathVariable("id") Long id, @ModelAttribute("producto") Producto productoActualizado) {
    // Obtener el producto existente por su ID
    Producto productoExistente = prodServ.obtenerProductoPorId(id);
    
    if (productoExistente != null) {
        // Actualizar los campos del producto existente con los datos del producto actualizado
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setMarca(productoActualizado.getMarca());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setStock(productoActualizado.getStock());
        
        // Llamar al servicio para guardar los cambios en la base de datos
        prodServ.editarProducto( id, productoExistente);
    }
    
    return "redirect:/verProductos"; // Redirige al listado de productos después de actualizar
}

    @GetMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        prodServ.eliminarProducto(id);
        return "redirect:/verProductos"; // Redirige al listado de productos después de eliminar
    }
    
   @PostMapping("/actualizarProductos")
    public String mostrarFormularioEditar(@RequestParam("id") Long id, Model model) {
        Producto producto = prodServ.obtenerProductoPorId(id);
        if (producto != null) {
            model.addAttribute("producto", producto); // Agregar el producto al modelo con el nombre "producto"
            return "actualizarProductos"; // Vista para editar producto
        } else {
            return "redirect:/verProductos"; // Redirige al listado si no se encuentra el producto
        }
    }
}
