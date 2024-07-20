package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.repository.ProductoRepository;
import com.imperialnet.inventiory.repository.VentaRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

@Service
public class VentaService implements IVentaService {

    @Autowired
    VentaRepository ventaRepo;
    @Autowired
    IUsuarioService usuSev;

    @Autowired
    ProductoRepository productoRepo; // Asegúrate de tener este repositorio

    @Autowired
    private EntityManager entityManager;

   @Override
@Transactional
public void registrarVenta(Venta venta, List<ItemVenta> itemsVenta, HttpSession session) {
    // Verificar el stock disponible para cada producto y asignar el precio unitario
    for (ItemVenta itemVenta : itemsVenta) {
        Producto producto = itemVenta.getProducto();
        // Asignar el precio unitario del producto al itemVenta
        itemVenta.setPrecioUnitario(producto.getPrecio()); // Asignar el precio del producto

        // Verificar stock
        if (producto.getStock() < itemVenta.getCantidad() || producto.getStock() == 0) {
            session.setAttribute("errorProd", "No queda Stock del producto que desea vender.");
            return;
        }
    }

    // Deducir el stock de cada producto
    for (ItemVenta itemVenta : itemsVenta) {
        Producto producto = itemVenta.getProducto();

        if (producto.getStock() > 0) {
            producto.setStock(producto.getStock() - itemVenta.getCantidad());
            productoRepo.save(producto); // Guardar el cambio en la base de datos
        }
    }

    // Asignar la lista de itemsVenta a la venta
    venta.setItemsVenta(itemsVenta);

    // Asignar la venta a cada ItemVenta para establecer la relación bidireccional
    for (ItemVenta itemVenta : itemsVenta) {
        itemVenta.setVenta(venta);
    }

    // Calcular el total de la venta si es necesario
    float total = calcularTotalVenta(itemsVenta);
    venta.setTotal(total);

    // Guardar la venta en la base de datos usando merge() si es necesario
    entityManager.merge(venta); // Reasocia la entidad detached con el contexto de persistencia
}
    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta obtenerVentaPorId(Long id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    public Venta editarVenta(Long id, Venta venta) {
        Venta ventaEditar = this.obtenerVentaPorId(id);
        if (ventaEditar != null) {
            ventaEditar.setCliente(venta.getCliente());
            ventaEditar.setFechaVenta(venta.getFechaVenta());
            ventaEditar.setItemsVenta(venta.getItemsVenta());
            ventaEditar.setTotal(venta.getTotal());
            ventaEditar.setUsuario(venta.getUsuario());
            ventaRepo.save(ventaEditar);
        }
        return null;
    }

    @Override
    public void eliminarVenta(Long id) {
        ventaRepo.deleteById(id);
    }

    @Override
    @Transactional
    public float calcularTotalVenta(List<ItemVenta> productosSeleccionados) {
        float total = 0;
        for (ItemVenta producto : productosSeleccionados) {
            total += producto.getProducto().getPrecio() * producto.getCantidad();
        }
        return total;
    }

    @Override
    public List<Venta> findByUsuarioId(Long usuarioId) {
        return ventaRepo.findByUsuarioId(usuarioId);
    }

    @Override // Método para obtener las ventas filtradas por mes
    public List<Venta> obtenerVentasPorMes(List<Venta> ventas, int mes) {
        return ventas.stream()
                .filter(venta -> venta.getFechaVenta().getMonthValue() == mes)
                .collect(Collectors.toList());
    }

    @Override
    public Cliente obtenerClienteQueMasComproEnMes(int mes, long idUser) {
        List<Venta> ventasDelMes = new ArrayList<>();


        // Filtrar las ventas del mes especificado
        List<Venta> todasLasVentas = ventaRepo.findByUsuarioId(idUser);
        for (Venta venta : todasLasVentas) {
            if (venta.getFechaVenta().getMonthValue() == mes) {
                ventasDelMes.add(venta);
            }
        }

        // Map para calcular el total de ventas por cliente
        Map<Cliente, Float> totalVentasPorCliente = new HashMap<>();

        for (Venta venta : ventasDelMes) {
            Cliente cliente = venta.getCliente();
            Float totalVentasCliente = totalVentasPorCliente.getOrDefault(cliente, 0f);
            totalVentasCliente += venta.getTotal();
            totalVentasPorCliente.put(cliente, totalVentasCliente);
        }

        // Encontrar el cliente que más compró
        Cliente clienteQueMasCompro = null;
        Float maxTotalVentas = 0f;

        for (Map.Entry<Cliente, Float> entry : totalVentasPorCliente.entrySet()) {
            if (entry.getValue() > maxTotalVentas) {
                maxTotalVentas = entry.getValue();
                clienteQueMasCompro = entry.getKey();
            }
        }

        return clienteQueMasCompro;
    }

    @Override
    public float obtenerMontoTotalComprasClienteEnMes(Long clienteId, int mes, long idUser) {
        float totalCompras = 0;

        // Obtener todas las ventas del cliente
        List<Venta> ventasCliente = new ArrayList<>();

        List<Venta> todasLasVentas = ventaRepo.findByUsuarioId( idUser);
        for (Venta venta : todasLasVentas) {
            if (venta.getCliente().getId().equals(clienteId) && venta.getFechaVenta().getMonthValue() == mes) {
                ventasCliente.add(venta);
            }
        }

        // Calcular el monto total de compras del cliente en el mes
        for (Venta venta : ventasCliente) {
            totalCompras = totalCompras +venta.getTotal();
        }

        return totalCompras;
    }

    @Override
    public float obtenerMontoTotalComprasEnMes(List<Venta> ventas) {

        float total=0;
        
        for(Venta venta:ventas){
            total+=venta.getTotal();
        }
        return total;
    }
    
      @Override
    public List<Object[]> obtenerTopProductosVendidos(int mes, Long usuarioId) {
        return entityManager.createQuery(
            "SELECT iv.producto.nombre, iv.producto.descripcion, SUM(iv.cantidad) as cantidadVendida " +
            "FROM Venta v JOIN v.itemsVenta iv " +
            "WHERE MONTH(v.fechaVenta) = :mes AND v.usuario.id = :usuarioId " +
            "GROUP BY iv.producto.id " +
            "ORDER BY cantidadVendida DESC", Object[].class)
            .setParameter("mes", mes)
            .setParameter("usuarioId", usuarioId)
            .setMaxResults(10)
            .getResultList();
    }
    
       @Override
    public List<Venta> obtenerVentasPorCliente(Long clienteId) {
        return ventaRepo.findByClienteId(clienteId);
    }
    
     @Override
    public List<Venta> obtenerVentasPorClienteYUsuario(Long clienteId, Long usuarioId) {
        return ventaRepo.findByClienteIdAndUsuarioId(clienteId, usuarioId);
    }
    
}