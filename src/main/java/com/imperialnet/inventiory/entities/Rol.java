
package com.imperialnet.inventiory.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jonii
 */

@Getter @Setter
@Entity
public class Rol {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String descripicion;

    public Rol() {
    }

    public Rol(int id, String descripicion) {
        this.id = id;
        this.descripicion = descripicion;
    }
    
    
}
