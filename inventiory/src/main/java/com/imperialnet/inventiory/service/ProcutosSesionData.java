package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.entities.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ProcutosSesionData implements Serializable {

    private List<ItemVenta> productosSeleccionados = new ArrayList<>();
    private List<Producto> productosAumento = new ArrayList<>();

    public List<ItemVenta> getProductosSeleccionados()
    {
        return productosSeleccionados;
    }

    public void agregarProducto(ItemVenta nuevoProducto, float cantidad)
    {
        // Verificar si el producto ya está en la lista
        for (ItemVenta item : productosSeleccionados) {
            if (item.getProducto().getId() == nuevoProducto.getProducto().getId()) {
                // El producto ya existe, aumentar la cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                return; // Salir del método
            }
        }

        // Si el producto no está en la lista, agregarlo normalmente
        productosSeleccionados.add(nuevoProducto);
    }

    public void agregarAproductosaAumentarPrecio(Producto producto) 
    {
        boolean exists = false;
        for (Producto prods : productosAumento) {
            if (prods.getId().equals(producto.getId())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            productosAumento.add(producto);
        }

    }

    public List<Producto> getListadoAumento() 
    {
        return productosAumento;
    }

    public void vaciar() 
    {
        productosSeleccionados.clear();
    }

}
