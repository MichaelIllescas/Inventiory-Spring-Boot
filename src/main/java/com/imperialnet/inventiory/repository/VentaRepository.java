
package com.imperialnet.inventiory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imperialnet.inventiory.entities.Venta;

/**
 *
 * @author jonii
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
    
}
