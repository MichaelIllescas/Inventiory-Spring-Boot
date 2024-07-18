/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.entities.Venta;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IVentaService {

    @Transactional
    public void registrarVenta(Venta venta, List<ItemVenta> itemsVenta, HttpSession sesion);

    public List<Venta> getVentas();

    public Venta obtenerVentaPorId(Long id);

    public Venta editarVenta(Long id, Venta venta);

    public void eliminarVenta(Long id);

    public List<Venta> findByUsuarioId(Long usuarioId);

    // Método para calcular el total de la venta
    public float calcularTotalVenta(List<ItemVenta> productosSeleccionados);

    public List<Venta> obtenerVentasPorMes(List<Venta> ventas, int mes);

    public Cliente obtenerClienteQueMasComproEnMes(int mes, long id);

    public float obtenerMontoTotalComprasClienteEnMes(Long clienteId, int mes, long idUser);
    
    public float obtenerMontoTotalComprasEnMes(List<Venta> ventas);

    public List<Object[]> obtenerTopProductosVendidos(int mes, Long usuarioId);

}
