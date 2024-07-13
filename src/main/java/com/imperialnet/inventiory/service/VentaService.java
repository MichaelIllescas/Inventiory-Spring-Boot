
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.repository.VentaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jonii
 */
@Service
public class VentaService implements IVentaService{
    @Autowired
    VentaRepository ventaRepo;
    
     @Autowired
    private EntityManager entityManager;

  

    public void registrarVenta(Venta venta, List<ItemVenta> itemsVenta) {
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
        Venta ventaEditar= this.obtenerVentaPorId(id);
        if(ventaEditar!=null){
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
            total += producto.getProducto().getPrecio()*producto.getCantidad();
        }
        return total;
    }
   
    
}
