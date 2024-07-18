
package com.imperialnet.inventiory.repository;

import com.imperialnet.inventiory.entities.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
     List<Producto> findByUsuarioId(Long usuarioId);
}
