package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Factura;
import com.imperialnet.inventiory.repository.FacturaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService implements IFacturaService{

    @Autowired
    FacturaRepository factuRepo;
    
    @Override
    public void crearFactura(Factura facura) 
    {
        factuRepo.save(facura);
    }

    @Override
    public List<Factura> getFacturaa() 
    {
        return factuRepo.findAll();
    }

    @Override
    public Factura obtenerFacturaPorId(Long id)
    {
        return factuRepo.findById(id).orElse(null);
    }

    @Override
    public Factura editarFatura(Long id, 
                                Factura factura)
    {
        Factura facturaEditar= this.obtenerFacturaPorId(id);
        if(facturaEditar!=null){
            facturaEditar.setFecha_emision(factura.getFecha_emision());
            facturaEditar.setNumero(factura.getNumero());
            facturaEditar.setVenta(factura.getVenta());
            return factuRepo.save(facturaEditar);
        }
        return null;
    }

    @Override
    public void eliminarFactura(Long id)
    {
        factuRepo.deleteById(id);
    }
    
}
