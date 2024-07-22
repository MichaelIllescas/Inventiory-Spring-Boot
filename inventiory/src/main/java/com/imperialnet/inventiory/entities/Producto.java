
package com.imperialnet.inventiory.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String marca;
    private String descripcion;
    private float precio;
    private int stock;
    private String categoria;
    private int estado;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)

    private Usuario usuario;
    

    public Producto() {
    }

    public Producto(Long id, String nombre,String marca, String descripcion, float precio, int stock, String categoria, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.estado = estado;
        this.marca = marca;
    }

    
}
