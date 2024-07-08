/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Rol;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IRolService {
    public void crearRol(Rol rol);
    public List<Rol> getRoles();
    public Rol obtenerRolPorId(Long id);
    public Rol editarRol(Long id, Rol rol);
    public void eliminarRol(Long id);
}
