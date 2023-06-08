package com.cristianpenaranda.apiRestDevsu.repository;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import com.cristianpenaranda.apiRestDevsu.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Cristian Peñaranda
 **/
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query(value = "SELECT m.movi_saldo FROM movimiento m INNER JOIN cuenta c ON (c.cuen_id = m.cuen_id) WHERE c.clie_id = ? AND c.cuen_id = ? ORDER BY m.movi_fecha DESC LIMIT 1", nativeQuery = true)
    String findSaldoActual(long idCliente, long idCuenta);

    @Query(value = "SELECT SUM(CAST(m.movi_valor AS INTEGER)) FROM movimiento m INNER JOIN cuenta c ON (c.cuen_id = m.cuen_id) WHERE c.clie_id = ? AND c.cuen_id = ? AND CAST(m.movi_fecha AS date) = CAST(NOW() AS date) AND CAST(m.movi_valor AS INTEGER) < 0 GROUP BY m.movi_valor", nativeQuery = true)
    List<Double> findMovimientoDelDia(long idCliente, long idCuenta);

    @Query(value = "SELECT CAST(m.movi_fecha AS DATE),p.pers_nombre,c.cuen_numero_cuenta,c.cuen_tipo_cuenta,c.cuen_saldo_inicial,c.cuen_estado,m.movi_valor,m.movi_saldo FROM movimiento m INNER JOIN cuenta c ON (c.cuen_id = m.cuen_id) INNER JOIN cliente cl ON (cl.clie_id = c.clie_id) INNER JOIN persona p ON (p.pers_id = cl.pers_id) WHERE c.clie_id = ? AND CAST(m.movi_fecha AS DATE) BETWEEN ? AND ? ORDER BY m.movi_fecha ASC", nativeQuery = true)
    List<Map<String,Object>> findByIdCliente(Long idCliente, Date fechaInicial, Date fechaFinal);
}
