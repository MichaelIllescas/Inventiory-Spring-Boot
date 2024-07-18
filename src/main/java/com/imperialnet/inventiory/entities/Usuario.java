
package com.imperialnet.inventiory.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
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
public class Usuario {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
   
    @Column(unique = true)
    private String nombreUsuario;
   
    private String clave;
    
    @Transient
    private String clave_repeat;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
    
    private int activo=1;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Producto> productos;
   
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cliente> clientes;
   
    
    
    // Relación con ventas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Venta> ventas;

    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String clave, String clave_repeat, Rol rol, int activo, List<Venta> ventas) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.clave_repeat = clave_repeat;
        this.rol = rol;
        this.activo = activo;
        this.ventas = ventas;
    }
    
    
    
}
