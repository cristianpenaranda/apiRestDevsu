package com.cristianpenaranda.apiRestDevsu.service;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;

import java.util.List;

/**
 * @author Cristian Peñaranda
 **/
public interface ClienteService {

    Cliente saveCliente(Cliente clienteRequest);

    Cliente updateCliente(Cliente clienteRequest);

    List<Cliente> getAllCliente();

    Cliente getCliente(long idCliente);

    void deleteCliente(long idCliente);
}
