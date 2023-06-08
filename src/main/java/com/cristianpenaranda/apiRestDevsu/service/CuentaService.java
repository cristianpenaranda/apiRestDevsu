package com.cristianpenaranda.apiRestDevsu.service;

import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;

import java.util.List;

/**
 * @author Cristian Peñaranda
 **/
public interface CuentaService {

    Cuenta saveCuenta(Cuenta cuentaRequest);

    Cuenta updateCuenta(Cuenta cuentaRequest);

    List<Cuenta> getAllCuenta();

    Cuenta getCuenta(long idCuenta);

    Cuenta getCuentaByNumeroCuenta(String idCuenta);

    void deleteCuenta(long idCuenta);
}
