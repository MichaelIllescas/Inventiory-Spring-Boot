/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String dni;
    private String cuil_cuit;
    
   
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> ventas;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)

    private Usuario usuario;
    
    private int estado=1;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String apellido, String direccion, String telefono, String dni, String cuil_cuit, List<Venta> ventas, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = dni;
        this.cuil_cuit = cuil_cuit;
        this.ventas = ventas;
        this.estado = estado;
    }
    
    
    
    
}
