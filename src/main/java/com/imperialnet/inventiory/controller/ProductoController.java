package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.service.IProductoService;
import com.imperialnet.inventiory.service.ProcutosSesionData;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private IProductoService prodServ;
      @Autowired
    ProcutosSesionData listado;

    // Método para crear un producto
    @PostMapping("/producto/crear")
    public String crearProducto(Producto producto,
                                HttpSession session)
    {
        prodServ.crearProducto(producto, session);
        return "redirect:/verProductos"; // Redirige a la página de listado de productos después de la creación
    }

    // Método para listar los productos del usuario
    @GetMapping("/verProductos")
    public String listarProductos(Model model, 
                                  HttpSession session)
    {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        List<Producto> listaProductos = prodServ.obtenerProductosPorUsuario(idUsuario);
        model.addAttribute("listadoProductos", listaProductos);
        return "verProductos"; // Nombre de la vista Thymeleaf
    }

    // Método para eliminar un producto por su ID
    @GetMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id)
    {
        prodServ.eliminarProducto(id);
        return "redirect:/verProductos"; // Redirige al listado de productos después de eliminar
    }

    // Método para mostrar el formulario de edición de producto
    @PostMapping("/actualizarProductos")
    public String mostrarFormularioEditar(@RequestParam("id") Long id, 
                                          Model model) 
    {
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
                            @RequestParam ("id")Long id )
    {
    
      prodServ.editarProducto(id, prod);
       return "redirect:/verProductos";
    }
    
    
    
    // Método para mostrar el formulario de actualización de stock
    @GetMapping("/actualizarStock")
    public String mostrarFormularioActualizarStock(Model model,
                                                   @RequestParam(value = "idProducto", required = false) Long idProducto,
                                                   HttpSession session) 
    {
        List<Producto> productos = prodServ.obtenerProductosPorUsuario((Long)session.getAttribute("idUsuario"));
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
    public String cargarProducto(@RequestParam("idProducto") Long idProducto) 
    {
        return "redirect:/actualizarStock?idProducto=" + idProducto;
    }

    // Método para actualizar el stock del producto
    @PostMapping("/actualizarStock")
    public String actualizarStock(@RequestParam("productoId") Long productoId,
                                  @RequestParam("cantidad") int cantidad,
                                  HttpSession session)
    {
        if (productoId == null) {
            session.setAttribute("errorProd", "Debe seleccionar un producto.");
            return "redirect:/actualizarStock";
        }

        prodServ.actualizarStock(productoId, cantidad);

        session.setAttribute("confirmationMessage", "El stock ha sido actualizado correctamente.");
        session.removeAttribute("errorProd"); // Limpiar el mensaje de error si existe
        return "redirect:/actualizarStock"; // Redirige a la misma página para mostrar la lista actualizada
    }
@GetMapping("/aplicarAumentos")
public String aplicarAumentos(HttpSession sesion,
                              Model model) {
     // Obtener y añadir la lista de productos y clientes al modelo
        List<Producto> productos = prodServ.obtenerProductosPorUsuario((Long)sesion.getAttribute("idUsuario"));
        model.addAttribute("productos", productos);

    
    return "aplicarAumentos";  // Esto debe coincidir con el nombre del archivo HTML en el directorio de plantillas
}

    @PostMapping("/agregarProductoPrecio")
    public String agregarProducto(@RequestParam(value = "idProducto", required = false) Long idProducto,
                                  Model model,
                                  
                                  HttpSession sesion)
    {
        // Validar si se seleccionó un producto válido
        if (idProducto == null) {
            sesion.setAttribute("errorProd", "Debe seleccionar un producto válido.");
            return "redirect:/aplicarAumentos"; // Redirige al formulario de registro de venta
        }

        // Obtener el producto seleccionado por su ID
        Producto productoSeleccionado = prodServ.obtenerProductoPorId(idProducto);

        // Validar si el producto seleccionado existe
        if (productoSeleccionado == null) {
            sesion.setAttribute("errorProd", "El producto seleccionado no existe.");
            return "redirect:/aplicarAumentos"; // Redirige al formulario de registro de venta
        }

        // Crear un nuevo ItemVenta con el producto y cantidad predeterminada
        Producto prod = prodServ.obtenerProductoPorId(idProducto);
        
        

        // Agregar el producto a la lista de productos seleccionados en la sesión
        listado.agregarAproductosaAumentarPrecio(prod);
        sesion.setAttribute("productosSeleccionados", listado.getListadoAumento());

        // Calcular y guardar el total de la venta en la sesión
        sesion.removeAttribute("errorProd");
        return "redirect:/aplicarAumentos"; // Redirige al formulario de registro de venta
    }
     @PostMapping("/eliminarProductoAumentos")
    public String eliminarProducto(@RequestParam("idProd") Long idProd,
                                   HttpSession sesion)
    {
        // Eliminar el producto seleccionado de la lista en la sesión
        listado.getListadoAumento().removeIf(producto -> producto.getId().equals(idProd));

        // Actualizar la lista de productos seleccionados y el total de la venta en la sesión
        sesion.setAttribute("productosSeleccionados", listado.getListadoAumento());
        return "redirect:/aplicarAumentos"; // Redirige al formulario de registro de venta
    }
    @PostMapping("/confirmarAumento")
     @Transactional
    public String aplicarPorcentajeAumento(HttpSession sesion){
   
     List<Producto> productosAumento = listado.getListadoAumento();
     // Validar si se seleccionaron productos para la venta
        if (productosAumento  == null || productosAumento .isEmpty()) {
            sesion.setAttribute("errorProd", "No se han seleccionado productos para la venta.");
            return "redirect:/aplicarAumentos"; // Redirige al formulario de registro de venta
        }
        return "";

     
    }
    


}
