
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonii
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{
    
}
