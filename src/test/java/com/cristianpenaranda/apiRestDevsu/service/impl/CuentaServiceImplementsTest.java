package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import com.cristianpenaranda.apiRestDevsu.entity.Persona;
import com.cristianpenaranda.apiRestDevsu.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CuentaServiceImplementsTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImplements cuentaServiceImplements;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cuenta = new Cuenta();
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setNombre("Nombre Persona Test");
        persona.setTelefono("000123456");
        persona.setDireccion("Direccion PruebaTest");
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setEstado("True");
        cliente.setContrasena("123456");
        cliente.setIdCliente(10L);
        cuenta.setIdCuenta(1L);
        cuenta.setNumeroCuenta("456789123");
        cuenta.setTipoCuenta("A");
        cuenta.setCliente(cliente);
        cuenta.setSaldoInicial("1000");
    }

    @Test
    void getAllCuenta() {
        when(cuentaServiceImplements.getAllCuenta()).thenReturn(Arrays.asList(cuenta));
        assertNotNull(cuentaServiceImplements.getAllCuenta());
    }
}