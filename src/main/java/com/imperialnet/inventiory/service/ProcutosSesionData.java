
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
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
    
     private List<Producto> productosSeleccionados = new ArrayList<>();

    public List<Producto> getProductosSeleccionados() {
        return productosSeleccionados;
    }
     
    public void agregarProducto(Producto prod){
        productosSeleccionados.add(prod);
    }
}
