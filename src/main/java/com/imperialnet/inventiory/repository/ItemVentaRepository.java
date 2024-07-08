
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonii
 */
@Repository
public interface ItemVentaRepository extends JpaRepository<ItemVenta, Long>{
    
}
