
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Venta;
import com.imperialnet.inventiory.repository.VentaRepository;
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
    @Override
    public void crearVenta(Venta venta) {
        ventaRepo.save(venta);
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
            ventaEditar.setItem_Venta(venta.getItem_Venta());
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
    
}
