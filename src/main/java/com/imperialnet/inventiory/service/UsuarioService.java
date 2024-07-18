/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.repository.UsuarioRepository;
import com.imperialnet.inventiory.util.PasswordUtils;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jonii
 */
@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    UsuarioRepository usuarioRepo;
   
    @Override
    public void crearUduario(Usuario usuario) {
        try {
            usuario.setClave(PasswordUtils.hashPassword(usuario.getClave()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
     usuarioRepo.save(usuario);
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Override
    public Usuario editarUsuario(Long id, Usuario usuario) {
        Usuario usuarioEditar =this.obtenerUsuarioPorId(id);
        if(usuarioEditar!=null){
            usuarioEditar.setNombreUsuario(usuario.getNombreUsuario());
            usuarioEditar.setRol(usuario.getRol());
            usuarioEditar.setVentas(usuario.getVentas());
            usuarioEditar.setActivo(usuario.getActivo());
            
            try {
                 usuario.setClave(PasswordUtils.hashPassword(usuario.getClave()));
             } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
             }
                usuarioRepo.save(usuario);
              }
            
           
        
        return null;
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepo.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorUserName(String usernombreUsuario) {
        return usuarioRepo.findByNombreUsuario(usernombreUsuario);
    }
     // Método para obtener la clave de un usuario por su ID
    public String obtenerClavePorId(Long usuarioId) {
        return usuarioRepo.findClaveById(usuarioId);
    }

    @Override
    public Usuario editarUsuarioSinClave(Long id, Usuario usuario) {
         Usuario usuarioEditar =this.obtenerUsuarioPorId(id);
        if(usuarioEditar!=null){
            usuarioEditar.setNombreUsuario(usuario.getNombreUsuario());
            usuarioEditar.setRol(usuario.getRol());
            usuarioEditar.setVentas(usuario.getVentas());
            usuarioEditar.setActivo(usuario.getActivo());
            usuario.setClave(usuarioRepo.findById(id).get().getClave()  );
        }
              usuarioRepo.save(usuario);
        return null;       
    }        
           
        
     public boolean verificarNombreUsisario(String nombreUsuario){
         List <Usuario>listado= usuarioRepo.findAll();
         for(Usuario usu: listado){
             if(usu.getNombreUsuario().equals(nombreUsuario)){
                 return true;
             }
         }
         return false;
     }

    @Override
    public void anularUsuario(Usuario usuario) {
        if(usuario.getActivo()==1){
        usuario.setActivo(0);
        }else{
        usuario.setActivo(1);
        }
        usuarioRepo.save(usuario);
    }
     
     
}

    
    
    
