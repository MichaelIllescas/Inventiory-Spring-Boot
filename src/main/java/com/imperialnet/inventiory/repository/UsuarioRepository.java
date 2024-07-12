/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonii
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
        
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
      String findClaveById(Long usuarioId);      
    
}
