
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author jonii
 */
@Component
@SessionScope
public class ProcutosSesionData implements Serializable{
    
     private List<ItemVenta> productosSeleccionados = new ArrayList<>();

    public List<ItemVenta> getProductosSeleccionados() {
        return productosSeleccionados;
    }
     


public void agregarProducto(ItemVenta nuevoProducto) {
    // Verificar si el producto ya está en la lista
    for (ItemVenta item : productosSeleccionados) {
        if (item.getProducto().getId() == nuevoProducto.getProducto().getId()) {
            // El producto ya existe, aumentar la cantidad
            item.setCantidad(item.getCantidad() + 1);
            return; // Salir del método
        }
    }
    
    // Si el producto no está en la lista, agregarlo normalmente
    productosSeleccionados.add(nuevoProducto);
}
}
