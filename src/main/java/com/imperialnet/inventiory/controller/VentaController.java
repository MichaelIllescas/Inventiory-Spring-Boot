package com.imperialnet.inventiory.controller;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.service.IClienteService;
import com.imperialnet.inventiory.service.IItemVentaService;
import com.imperialnet.inventiory.service.IProductoService;
import com.imperialnet.inventiory.service.IUsuarioService;
import com.imperialnet.inventiory.service.IVentaService;
import com.imperialnet.inventiory.service.ProcutosSesionData;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
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
    IUsuarioService usuServ;

    @Autowired
    IItemVentaService itemServ;

    @Autowired
    IVentaService ventaServ;

    @Autowired
    ProcutosSesionData listado;

    @GetMapping("/menuVentas")
    public String menu() {
        return "menuVentas";
    }

    @GetMapping("/registrarVenta")
    public String panelVenta(Model model, HttpSession sesion)
    {
        List<Producto> productos = prodServ.getProductos();
        model.addAttribute("productos", productos);
        List<Cliente> clientes = cliServ.getClientes();
        model.addAttribute("clientes", clientes);
        return "registrarVenta";
    }

    @GetMapping("/seleccionarCliente")
    public String seleccionarCliente(Model model,
            HttpSession sesion,
            @RequestParam(value = "dniCliente", required = false) String dni)
    {
        if (dni == null || dni.isEmpty()) {
            sesion.setAttribute("errorCli", "Debe seleccionar un cliente");
            return "redirect:/registrarVenta"; // Asegúrate de que esta sea la vista correcta
        }

        Cliente clienteSeleccionado = cliServ.findByDni(dni);

        if (clienteSeleccionado == null) {
            sesion.setAttribute("errorCli", "El cliente no existe");
            return "redirect:/registrarVenta";
        }

        sesion.setAttribute("cliente", clienteSeleccionado);
        sesion.removeAttribute("errorCli");
        return "redirect:/registrarVenta";
    }

    @PostMapping("/agregarProducto")
    public String agregarProducto(@RequestParam(value = "idProducto", required = false) String idProductostr,
                                    Model model,
                                    HttpSession sesion,
                                    ItemVenta item) 
    {

        Long idProducto = null;
        try {
            idProducto = Long.parseLong(idProductostr);
        } catch (NumberFormatException e) {
            sesion.setAttribute("errorProd", "Debe seleccionar un producto válido.");
            return "redirect:/registrarVenta";
        }

        if (idProducto == null) {
            sesion.setAttribute("errorProd", "Debe seleccionar al menos un producto.");
            return "redirect:/registrarVenta";
        }
        if (idProducto == null) {
            sesion.setAttribute("errorProd", "Debe seleccionar al menos un producto.");
            return "redirect:/registrarVenta";
        }

        Producto productoSeleccionado = prodServ.obtenerProductoPorId(idProducto);
        if (productoSeleccionado == null) {
            sesion.setAttribute("errorProd", "El producto seleccionado no existe.");
            return "redirect:/registrarVenta";
        }
        item.setCantidad(1);
        item.setProducto(productoSeleccionado);
        listado.agregarProducto(item);
        sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());
        sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
        sesion.removeAttribute("errorProd");
        return "redirect:/registrarVenta";
    }

    @PostMapping("/eliminarProducto")
    public String eliminarProducto(@RequestParam("idProd") Long idProd,
                                   HttpSession sesion)
    {
        listado.getProductosSeleccionados().removeIf(producto -> producto.getProducto().getId().equals(idProd));

        sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());
        sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
        return "redirect:/registrarVenta";
    }

    @PostMapping("/confirmarVenta")
    @Transactional
    public String confirmarVenta(HttpSession sesion) {
        // Obtener la fecha actual
        LocalDate fechaDeHoyLocalDate = LocalDate.now();

        // Obtener cliente de la sesión
        Cliente cliente = (Cliente) sesion.getAttribute("cliente");

        // Validar si el cliente está presente en la sesión
        if (cliente == null) {
            sesion.setAttribute("errorCli", "No se ha seleccionado un cliente para la venta.");
            return "redirect:/registrarVenta";
        }

        // Obtener usuario de la sesión
        Long idUsuario = (Long) sesion.getAttribute("idUsuario");
        Usuario usuario = usuServ.obtenerUsuarioPorId(idUsuario);

        // Validar si el usuario está presente en la sesión
        if (usuario == null) {
            sesion.setAttribute("error", "No se ha iniciado sesión correctamente.");
            return "redirect:/login"; // Redirigir al login o a donde corresponda
        }

        // Obtener productos seleccionados de la sesión
        List<ItemVenta> productosSeleccionados = listado.getProductosSeleccionados();

        // Validar si hay productos seleccionados
        if (productosSeleccionados == null || productosSeleccionados.isEmpty()) {
            sesion.setAttribute("errorProd", "No se han seleccionado productos para la venta.");
            return "redirect:/registrarVenta";
        }

        // Crear la venta y registrarla
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setFechaVenta(fechaDeHoyLocalDate);

        ventaServ.registrarVenta(venta, productosSeleccionados, sesion);

        String errorMessage = (String) sesion.getAttribute("errorProd");
        if (errorMessage != null) {

            return "redirect:/registrarVenta"; // Redirigir a una página de venta
        }

        // Limpiar atributos de sesión después de registrar la venta
        sesion.removeAttribute("cliente");
        sesion.removeAttribute("total");
        sesion.removeAttribute("errorProd");
        sesion.removeAttribute("errorCli");
        //se vacia el listado para evitar conflictos en proximas ventas
        listado.vaciar();
        sesion.setAttribute("confirmacion", "Se ha registrado la venta correctamente.");
        return "redirect:/registrarVenta";
    }

    @GetMapping("/verVentas")
    public String verVentas(HttpSession sesion, Model model) {
        model.addAttribute("ventas", ventaServ.getVentas());
        System.out.println("ventas:");
        for (Venta venta : ventaServ.getVentas()) {
            System.out.println("id:" + venta.getId());
        }
        return "verVentas";
    }

    @PostMapping("/eliminarVenta")
    public String eliminarVenta(@RequestParam("idVta") Long idVta,
                                HttpSession sesion,
                                Model model)
    {
        ventaServ.eliminarVenta(idVta);
        sesion.setAttribute("mensajeConfirm", "Se ha eliminado Correctamente");
        
        return "redirect:/verVentas";
    }
    
     @PostMapping("/ventasCliente")
    public String cargarComprasCliente(@RequestParam("idCli") Long idCli,
                                HttpSession sesion,
                                Model model)
    {
        Cliente cliente=cliServ.obtenerClientePorId(idCli);
        String nomApe=cliente.getNombre() + " " + cliente.getApellido();
        List<Venta> ventas=cliente.getVentas();
        model.addAttribute("ventas", ventas);
        model.addAttribute("cli", nomApe);
        
        return "verComprasCliente";
    }


}