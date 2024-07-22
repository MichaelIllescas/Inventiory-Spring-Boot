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
    public String menu() 
    {
        return "menuVentas"; // Retorna la vista del menú de ventas
    }

    @GetMapping("/registrarVenta")
    public String panelVenta(Model model,
                             HttpSession sesion)
    {
        // Obtener y añadir la lista de productos y clientes al modelo
        List<Producto> productos = prodServ.obtenerProductosPorUsuario((Long) sesion.getAttribute("idUsuario"));
        List<Cliente> clientes = cliServ.obtenerClientesPorUsuario((Long) sesion.getAttribute("idUsuario"));

        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);
        return "registrarVenta"; // Retorna la vista de registro de venta
    }

    @GetMapping("/seleccionarCliente")
    public String seleccionarCliente(Model model,
                                     @RequestParam(value = "dniCliente", required = false) String dni,
                                     HttpSession sesion) 
    {
        // Validar si se proporcionó un DNI válido
        if (dni == null || dni.isEmpty()) {
            sesion.setAttribute("errorCli", "Debe seleccionar un cliente.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Buscar el cliente por su DNI en la base de datos
        Cliente clienteSeleccionado = cliServ.findByDni(dni, (Long) sesion.getAttribute("idUsuario"));

        // Validar si se encontró un cliente válido
        if (clienteSeleccionado == null) {
            sesion.setAttribute("errorCli", "El cliente no existe.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Guardar el cliente seleccionado en la sesión y limpiar errores previos
        sesion.setAttribute("cliente", clienteSeleccionado);
        sesion.removeAttribute("errorCli");
        return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
    }

    @PostMapping("/agregarProducto")
    public String agregarProducto(@RequestParam(value = "idProducto", required = false) Long idProducto,
                                  Model model,
                                  @RequestParam(value = "cant") int cant,
                                  HttpSession sesion) 
    {
        // Validar si se seleccionó un producto válido
        if (idProducto == null) {
            sesion.setAttribute("errorProd", "Debe seleccionar un producto válido.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Obtener el producto seleccionado por su ID
        Producto productoSeleccionado = prodServ.obtenerProductoPorId(idProducto);

        // Validar si el producto seleccionado existe
        if (productoSeleccionado == null) {
            sesion.setAttribute("errorProd", "El producto seleccionado no existe.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }
        // Validar si el producto seleccionado existe
        if (productoSeleccionado.getStock() == 0) {
            sesion.setAttribute("errorProd", "No posee Stock disponible de " + productoSeleccionado.getNombre());
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Crear un nuevo ItemVenta con el producto y cantidad predeterminada
        ItemVenta item = new ItemVenta();
        item.setCantidad(cant);
        item.setProducto(productoSeleccionado);

        // Agregar el producto a la lista de productos seleccionados en la sesión
        listado.agregarProducto(item);
        sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());

        // Calcular y guardar el total de la venta en la sesión
        sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
        sesion.removeAttribute("errorProd");
        return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
    }

    @PostMapping("/eliminarProducto")
    public String eliminarProducto(@RequestParam("idProd") Long idProd,
                                   HttpSession sesion)
    {
        // Eliminar el producto seleccionado de la lista en la sesión
        listado.getProductosSeleccionados().removeIf(producto -> producto.getProducto().getId().equals(idProd));

        // Actualizar la lista de productos seleccionados y el total de la venta en la sesión
        sesion.setAttribute("productosSeleccionados", listado.getProductosSeleccionados());
        sesion.setAttribute("total", ventaServ.calcularTotalVenta(listado.getProductosSeleccionados()));
        return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
    }

    @PostMapping("/confirmarVenta")
    @Transactional
    public String confirmarVenta(HttpSession sesion,
                                 Model model,
                                 @RequestParam(value = "observaciones") String obs)
    {
        // Obtener la fecha actual
        LocalDate fechaDeHoyLocalDate = LocalDate.now();

        // Obtener cliente y usuario de la sesión
        Cliente cliente = (Cliente) sesion.getAttribute("cliente");
        Long idUsuario = (Long) sesion.getAttribute("idUsuario");
        Usuario usuario = usuServ.obtenerUsuarioPorId(idUsuario);

        // Validar la presencia de cliente y usuario en la sesión
        if (cliente == null) {
            sesion.setAttribute("errorCli", "No se ha seleccionado un cliente para la venta.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Obtener la lista de productos seleccionados de la sesión
        List<ItemVenta> productosSeleccionados = listado.getProductosSeleccionados();

        // Validar si se seleccionaron productos para la venta
        if (productosSeleccionados == null || productosSeleccionados.isEmpty()) {
            sesion.setAttribute("errorProd", "No se han seleccionado productos para la venta.");
            return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
        }

        // Validar stock de los productos seleccionados
        for (ItemVenta item : productosSeleccionados) {
            Producto producto = item.getProducto();
            int cantidadSeleccionada = item.getCantidad();
            int stockDisponible = producto.getStock(); // Asume que tienes un método getStock() en Producto

            if (cantidadSeleccionada > stockDisponible) {
                // Si el stock no es suficiente, agregar un mensaje de error
                sesion.setAttribute("errorProd", "No hay suficiente stock para el producto: " + producto.getNombre());
                sesion.removeAttribute("confirmacion");
                return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
            }
        }

        // Crear una nueva venta y registrarla
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setObservaciones(obs);
        venta.setFechaVenta(fechaDeHoyLocalDate);

        ventaServ.registrarVenta(venta, productosSeleccionados, sesion);

        // Limpiar atributos de sesión después de registrar la venta
        sesion.removeAttribute("cliente");
        sesion.removeAttribute("total");
        sesion.removeAttribute("errorProd");
        sesion.removeAttribute("errorCli");
        listado.vaciar(); // Vaciar la lista de productos seleccionados en la sesión

        // Establecer confirmación de venta exitosa en la sesión
        sesion.setAttribute("confirmacionVenta", "Se ha registrado la venta correctamente.");
        return "redirect:/registrarVenta"; // Redirige al formulario de registro de venta
    }

    @GetMapping("/verVentas")
    public String verVentas(HttpSession sesion, 
                            Model model) 
    {
        // Obtener y añadir las ventas del usuario al modelo
        model.addAttribute("ventas", ventaServ.findByUsuarioId((Long) sesion.getAttribute("idUsuario")));
        return "verVentas"; // Retorna la vista de visualización de ventas
    }

    @PostMapping("/eliminarVenta")
    public String eliminarVenta(@RequestParam("idVta") Long idVta,
                                HttpSession sesion)
    {
        // Eliminar la venta por su ID
        ventaServ.eliminarVenta(idVta);

        // Establecer mensaje de confirmación en la sesión
        sesion.setAttribute("mensajeConfirm", "Se ha eliminado correctamente");
        return "redirect:/verVentas"; // Redirige a la vista de visualización de ventas
    }

    @PostMapping("/ventasCliente")
    public String cargarComprasCliente(@RequestParam("idCli") Long idCli,
                                        HttpSession sesion,
                                        Model model)
    {
        // Obtener y añadir las ventas del cliente al modelo
        Cliente cliente = cliServ.obtenerClientePorId(idCli);
        String nomApe = cliente.getNombre() + " " + cliente.getApellido();
        List<Venta> ventas = ventaServ.obtenerVentasPorClienteYUsuario(cliente.getId(), (Long) sesion.getAttribute("idUsuario"));
        model.addAttribute("ventas", ventas);
        model.addAttribute("cli", nomApe);
        return "verComprasCliente"; // Retorna la vista de visualización de compras del cliente
    }
}
