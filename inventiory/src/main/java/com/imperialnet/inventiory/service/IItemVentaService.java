package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import jakarta.transaction.Transactional;
import java.util.List;

public interface IItemVentaService {

    @Transactional
    public void crearItemVenta(ItemVenta item);

    public List<ItemVenta> getItems();

    public ItemVenta obtenerItemVentaPorId(Long id);

    public ItemVenta editarItemVenta(Long id, ItemVenta item);

    public void eliminarItemVenta(Long id);
}
