
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import java.util.List;


public interface IProductoService {
    
    public void crearProducto(Producto producto);
    public List<Producto> getProductos();
    public Producto obtenerProductoPorId(Long id);
    public Producto editarProducto(Long id, Producto producto);
    public void eliminarProducto(Long id);
}
