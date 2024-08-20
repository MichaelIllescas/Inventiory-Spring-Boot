package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.repository.ProductoRepository;
import com.imperialnet.inventiory.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository prodRepo;
    @Autowired
    private IUsuarioService usuServ;

    @Override
    public void crearProducto(Producto producto, HttpSession sesion) 
    {
        producto.setUsuario(usuServ.obtenerUsuarioPorId((Long) sesion.getAttribute("idUsuario")));
        prodRepo.save(producto);
    }

    @Override
    public List<Producto> getProductos() 
    {
        return prodRepo.findAll();
    }

    @Override
    public Producto obtenerProductoPorId(Long id) 
    {
        Optional<Producto> optionalProducto = prodRepo.findById(id);
        return optionalProducto.orElse(null);
    }

    @Override
    public Producto editarProducto(Long id, 
                                   Producto producto) 
    {
        Optional<Producto> optionalProducto = prodRepo.findById(id);
        if (optionalProducto.isPresent()) {
            Producto existingProducto = optionalProducto.get();
            existingProducto.setNombre(producto.getNombre());
            existingProducto.setMarca(producto.getMarca());
            existingProducto.setDescripcion(producto.getDescripcion());
            existingProducto.setCategoria(producto.getCategoria());
            existingProducto.setPrecio(producto.getPrecio());
            existingProducto.setStock(producto.getStock());
            return prodRepo.save(existingProducto);
        }
        return null;
    }

    @Override
    public void eliminarProducto(Long id) 
    {
        prodRepo.deleteById(id);
    }

    public List<Producto> obtenerProductosPorUsuario(Long usuarioId)
    {
        return prodRepo.findByUsuarioId(usuarioId);
    }

    public void actualizarStock(Long id, float cantidad) 
    {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setStock(cantidad);
            prodRepo.save(producto);
        }
    }

    @Override
    public Float obtenerCapital(Long idUsuario) {
            List<Producto> productos = this.obtenerProductosPorUsuario(idUsuario);

            Float capital=0F;
          // Verificar si la lista de productos no es null
    // Verificar si la lista de productos no es null
    
        for (Producto producto : productos) {
            // Manejar el caso de valores nulos para precio y stock
            Float precio = producto.getPrecio();
            Float stock = producto.getStock();

            // Asegurarse de que precio y stock no sean null
            
               capital += (precio * stock);
          
    }
    
        return capital;
    }
}
