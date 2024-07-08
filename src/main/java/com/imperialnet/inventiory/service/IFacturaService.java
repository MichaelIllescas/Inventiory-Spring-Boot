/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Factura;
import com.imperialnet.inventiory.entities.ItemVenta;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IFacturaService {
    public void crearFactura(Factura facura);
    public List<Factura> getFacturaa();
    public Factura obtenerFacturaPorId(Long id);
    public Factura editarFatura(Long id, Factura factura);
    public void eliminarFactura(Long id);
    
}
