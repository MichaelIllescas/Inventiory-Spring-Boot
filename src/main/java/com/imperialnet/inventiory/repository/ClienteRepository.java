
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonii
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
