package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.service.IProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private IProductoService prodServ;

    // Método para crear un producto
    @PostMapping("/producto/crear")
    public String crearProducto(Producto producto, HttpSession session) {
        prodServ.crearProducto(producto, session);
        return "redirect:/verProductos"; // Redirige a la página de listado de productos después de la creación
    }

    // Método para listar los productos del usuario
    @GetMapping("/verProductos")
    public String listarProductos(Model model, HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        List<Producto> listaProductos = prodServ.obtenerProductosPorUsuario(idUsuario);
        model.addAttribute("listadoProductos", listaProductos);
        return "verProductos"; // Nombre de la vista Thymeleaf
    }

    // Método para eliminar un producto por su ID
    @GetMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        prodServ.eliminarProducto(id);
        return "redirect:/verProductos"; // Redirige al listado de productos después de eliminar
    }

    // Método para mostrar el formulario de edición de producto
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

    
    @PostMapping("/actualizar")
    public String actualizar(Producto prod, 
                            @RequestParam ("id")Long id ){
    
      prodServ.editarProducto(id, prod);
       return "redirect:/verProductos";
    }
    
    
    
    // Método para mostrar el formulario de actualización de stock
    @GetMapping("/actualizarStock")
    public String mostrarFormularioActualizarStock(Model model,
                                                   @RequestParam(value = "idProducto", required = false) Long idProducto,
                                                   HttpSession session) {
        List<Producto> productos = prodServ.getProductos();
        Producto selectedProduct = null;
        Integer stockActual = null;

        if (idProducto != null) {
            selectedProduct = prodServ.obtenerProductoPorId(idProducto);
            stockActual = selectedProduct != null ? selectedProduct.getStock() : null;
        }

        model.addAttribute("productos", productos);
        model.addAttribute("selectedProduct", selectedProduct);
        model.addAttribute("stockActual", stockActual);

        // Añadir el mensaje de confirmación si existe
        Object confirmationMessage = session.getAttribute("confirmationMessage");
        if (confirmationMessage != null) {
            model.addAttribute("confirmationMessage", confirmationMessage);
            session.removeAttribute("confirmationMessage"); // Eliminar el mensaje después de mostrarlo
        }

        session.removeAttribute("errorProd"); // Limpiar el mensaje de error si existe
        return "actualizarStock"; // Nombre del archivo HTML Thymeleaf
    }

    // Método para cargar el formulario de actualización de stock
    @PostMapping("/cargarProducto")
    public String cargarProducto(@RequestParam("idProducto") Long idProducto) {
        return "redirect:/actualizarStock?idProducto=" + idProducto;
    }

    // Método para actualizar el stock del producto
    @PostMapping("/actualizarStock")
    public String actualizarStock(@RequestParam("productoId") Long productoId,
                                  @RequestParam("cantidad") int cantidad,
                                  HttpSession session) {
        if (productoId == null) {
            session.setAttribute("errorProd", "Debe seleccionar un producto.");
            return "redirect:/actualizarStock";
        }

        prodServ.actualizarStock(productoId, cantidad);

        session.setAttribute("confirmationMessage", "El stock ha sido actualizado correctamente.");
        session.removeAttribute("errorProd"); // Limpiar el mensaje de error si existe
        return "redirect:/actualizarStock"; // Redirige a la misma página para mostrar la lista actualizada
    }
    
    

}
