package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Persona;
import com.cristianpenaranda.apiRestDevsu.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClienteServiceImplementsTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImplements clienteServiceImplements;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cliente = new Cliente();
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setNombre("Nombre Persona Test");
        persona.setTelefono("000123456");
        persona.setDireccion("Direccion PruebaTest");
        cliente.setPersona(persona);
        cliente.setEstado("True");
        cliente.setContrasena("123456");
        cliente.setIdCliente(10L);
    }

    @Test
    void getAllCliente() {
        when(clienteServiceImplements.getAllCliente()).thenReturn(Arrays.asList(cliente));
        assertNotNull(clienteServiceImplements.getAllCliente());
    }

}