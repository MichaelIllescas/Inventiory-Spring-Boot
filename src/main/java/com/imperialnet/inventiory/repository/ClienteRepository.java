
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonii
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    public Cliente findByNombre(String nombre);
    public Cliente findByDni(String dni);
    List<Cliente> findByUsuarioId(Long usuarioId);
    
}
