/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.entities.Venta;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IVentaService {
    public void crearVenta(Venta venta);
    public List<Venta> getVentas();
    public Venta obtenerVentaPorId(Long id);
    public Venta editarVenta(Long id, Venta venta);
    public void eliminarVenta(Long id);
    
    
     // Método para calcular el total de la venta
    public float calcularTotalVenta(List<Producto> productosSeleccionados) ;
        
}
