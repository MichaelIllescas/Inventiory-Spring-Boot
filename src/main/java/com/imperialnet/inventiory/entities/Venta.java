
package com.imperialnet.inventiory.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jonii
 */

@Getter
@Setter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date fechaVenta;
    private float total;
    @OneToOne
    private Cliente cliente;
    @OneToOne
    private Usuario usuario;
    @OneToMany
    private List<ItemVenta> item_Venta;

    public Venta() {
    }

    public Venta(Long id, Date fechaVenta, float total, Cliente cliente, Usuario usuario, List<ItemVenta> item_Venta) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.cliente = cliente;
        this.usuario = usuario;
        this.item_Venta = item_Venta;
    }
 
    
    
}
