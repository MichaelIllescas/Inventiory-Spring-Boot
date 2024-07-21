package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import com.imperialnet.inventiory.repository.ItemVentaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemVentaService implements IItemVentaService {

    @Autowired
    ItemVentaRepository itemRepo;

    @Override
    @Transactional
    public void crearItemVenta(ItemVenta item)
    {
        itemRepo.save(item);

    }

    @Override
    public List<ItemVenta> getItems()
    {
        return itemRepo.findAll();
    }

    @Override
    public ItemVenta obtenerItemVentaPorId(Long id)
    {
        return itemRepo.findById(id).orElse(null);
    }

    @Override
    public ItemVenta editarItemVenta(Long id,
                                    ItemVenta item) 
    {
        ItemVenta itemEditar = this.obtenerItemVentaPorId(id);
        if (itemEditar != null) {

            itemEditar.setCantidad(item.getCantidad());
            itemEditar.setProducto(item.getProducto());
            return itemRepo.save(itemEditar);
        }
        return null;
    }

    @Override
    public void eliminarItemVenta(Long id) 
    {
        itemRepo.deleteById(id);
    }

}
