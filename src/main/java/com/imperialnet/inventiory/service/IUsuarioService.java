
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Usuario;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IUsuarioService {
    public void crearUduario(Usuario usuario);
    public List<Usuario> getUsuarios();
    public Usuario obtenerUsuarioPorId(Long id);
    public Usuario editarUsuario(Long id, Usuario usuario);
    public void eliminarUsuario(Long id);
}
