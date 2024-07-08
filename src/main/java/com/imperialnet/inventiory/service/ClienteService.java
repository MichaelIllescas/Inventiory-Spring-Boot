/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jonii
 */
@Service
public class ClienteService implements IClienteService{

    @Autowired
    ClienteRepository cliRepo;
    
    @Override
    public void crearCliente(Cliente cliente) {
        cliRepo.save(cliente);
    }

    @Override
    public List<Cliente> getClientes() {
        return cliRepo.findAll();
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        
        return cliRepo.findById(id).orElse(null);
     }

    @Override
    public Cliente editarCliente(Long id, Cliente cliente) {
        Cliente clienteOriginal= this.obtenerClientePorId(id);
        if(clienteOriginal!= null){
            clienteOriginal.setNombre(cliente.getNombre());
            clienteOriginal.setApellido(cliente.getApellido());
            clienteOriginal.setDni(cliente.getDni());
            clienteOriginal.setDireccion(cliente.getDireccion());
            clienteOriginal.setCuil_cuit(cliente.getCuil_cuit());
            clienteOriginal.setEstado(cliente.getEstado());
            clienteOriginal.setTelefono(cliente.getTelefono());
            clienteOriginal.setVentas(cliente.getVentas());
             return cliRepo.save(clienteOriginal);
        }
        return null;
        
    }

    @Override
    public void eliminarCliente(Long id) {
        cliRepo.deleteById(id);
    }
    
    @Override
    public void editarCliente(Cliente cliente) {
    Cliente clienteExistente = this.obtenerClientePorId(cliente.getId());
    if (clienteExistente != null) {
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setCuil_cuit(cliente.getCuil_cuit());
        clienteExistente.setEstado(cliente.getEstado());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setVentas(cliente.getVentas());

         cliRepo.save(clienteExistente);
    }
}
}
