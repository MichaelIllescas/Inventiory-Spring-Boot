/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.entities.Rol;
import com.imperialnet.inventiory.repository.RolRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jonii
 */
@Service
public class RolService implements IRolService{

    @Autowired
    RolRepository rolRepo;
    
    @Override
    public void crearRol(Rol rol) {
        rolRepo.save(rol);
    }

    @Override
    public List<Rol> getRoles() {
        return rolRepo.findAll();
    }

    @Override
    public Rol obtenerRolPorId(Long id) {
        return rolRepo.findById(id).orElse(null);
    }

    @Override
    public Rol editarRol(Long id, Rol rol) {
         Optional<Rol> rolEditar = rolRepo.findById(id);
         if (rolEditar.isPresent()) {
            Rol existenteRol = rolEditar.get();
            existenteRol.setDescripicion(rol.getDescripicion());
          return rolRepo.save(existenteRol);
        }
        return null;
    }
    

    @Override
    public void eliminarRol(Long id) {
        rolRepo.deleteById(id);
    }
    
}
