package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public interface IProductoService {

    public void crearProducto(Producto producto, HttpSession sesion);

    public List<Producto> getProductos();

    public Producto obtenerProductoPorId(Long id);

    public Producto editarProducto(Long id, Producto producto);

    public void eliminarProducto(Long id);

    public List<Producto> obtenerProductosPorUsuario(Long usuarioId);

    public void actualizarStock(Long id, int cantidad);
}
