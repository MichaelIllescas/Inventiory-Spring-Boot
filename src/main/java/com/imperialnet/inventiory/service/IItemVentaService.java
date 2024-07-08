
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.ItemVenta;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IItemVentaService {
    public void crearItemVenta(ItemVenta item);
    public List<ItemVenta> getItems();
    public ItemVenta obtenerItemVentaPorId(Long id);
    public ItemVenta editarItemVenta(Long id, ItemVenta item);
    public void eliminarItemVenta(Long id);
}
