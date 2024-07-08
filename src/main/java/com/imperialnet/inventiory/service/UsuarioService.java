/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Usuario;
import com.imperialnet.inventiory.repository.UsuarioRepository;
import java.util.List;
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
            usuarioEditar.setClave(usuario.getClave());
            usuarioEditar.setRol(usuario.getRol());
            usuarioEditar.setVentas(usuario.getVentas());
            usuarioEditar.setActivo(usuario.getActivo());
            usuarioRepo.save(usuarioEditar);
        }
        return null;
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepo.deleteById(id);
    }
    
}
