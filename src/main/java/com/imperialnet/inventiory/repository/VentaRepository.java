
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imperialnet.inventiory.entities.Venta;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jonii
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
    
    List<Venta> findByUsuarioId(Long usuarioId);
 List<Venta> findByClienteId(Long clienteId);
     List<Venta> findByClienteIdAndUsuarioId(Long clienteId, Long usuarioId);

   
    
}
