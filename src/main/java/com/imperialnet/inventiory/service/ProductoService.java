package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Producto;
import com.imperialnet.inventiory.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository prodRepo;

    @Override
    public void crearProducto(Producto producto) {
        prodRepo.save(producto);
    }

    @Override
    public List<Producto> getProductos() {
        return prodRepo.findAll();
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        Optional<Producto> optionalProducto = prodRepo.findById(id);
        return optionalProducto.orElse(null);
    }

    @Override
    public Producto editarProducto(Long id, Producto producto) {
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
    public void eliminarProducto(Long id) {
        prodRepo.deleteById(id);
    }
}
