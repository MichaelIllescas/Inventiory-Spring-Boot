/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Cliente;
import java.util.List;

/**
 *
 * @author jonii
 */
public interface IClienteService {
      
    public void crearCliente(Cliente cliente);
    public List<Cliente> getClientes();
    public Cliente obtenerClientePorId(Long id);
    public Cliente editarCliente(Long id, Cliente cliente);
    public void editarCliente(Cliente cliente);
    public void eliminarCliente(Long id);
}
