package com.cristianpenaranda.apiRestDevsu.repository;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Cristian Peñaranda
 **/
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "SELECT * FROM cuenta cuen WHERE cuen.cuen_numero_cuenta = ?", nativeQuery = true)
    Cuenta findByNumeroCuenta(String numeroCuenta);
}
