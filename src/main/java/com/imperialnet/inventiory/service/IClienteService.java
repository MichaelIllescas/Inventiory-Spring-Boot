package com.imperialnet.inventiory.service;

import com.imperialnet.inventiory.entities.Cliente;
import com.imperialnet.inventiory.entities.Producto;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public interface IClienteService {

    public void crearCliente(Cliente cliente, HttpSession sesion);

    public List<Cliente> getClientes();

    public Cliente obtenerClientePorId(Long id);

    public Cliente editarCliente(Long id, Cliente cliente);

    public void editarCliente(Cliente cliente);

    public void eliminarCliente(Long id);

    public List<Cliente> obtenerClientesPorUsuario(Long usuarioId);

    public Cliente findByDni(String dni, Long usuarioId);

    public Cliente findByNombre(String nombre, Long usuarioId);
}
