package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public void crearUduario(Usuario usuario);

    public List<Usuario> getUsuarios();

    public Usuario obtenerUsuarioPorId(Long id);

    public Usuario editarUsuario(Long id, Usuario usuario);

    public Usuario editarUsuarioSinClave(Long id, Usuario usuario);

    public boolean verificarNombreUsisario(String nombreUsuario);

    public void eliminarUsuario(Long id);

    public Optional<Usuario> buscarUsuarioPorUserName(String usernombreUsuario);

    public String obtenerClavePorId(Long usuarioId);

    public void anularUsuario(Usuario usuario);
}
