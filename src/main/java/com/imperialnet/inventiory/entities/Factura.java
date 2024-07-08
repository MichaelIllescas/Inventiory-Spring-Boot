/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jonii
 */
@Getter
@Setter
@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private Date fecha_emision;
    @OneToOne
    private Venta venta;

    public Factura() {
    }

    public Factura(Long id, int numero, Date fecha_emision, Venta venta) {
        this.id = id;
        this.numero = numero;
        this.fecha_emision = fecha_emision;
        this.venta = venta;
    }
    
    
    

}
