package com.cristianpenaranda.apiRestDevsu.service;

import com.cristianpenaranda.apiRestDevsu.entity.Movimiento;

import java.util.List;

/**
 * @author Cristian Peñaranda
 **/
public interface MovimientoService {

    Movimiento saveMovimiento(Movimiento movimientoRequest);

    Movimiento updateMovimiento(Movimiento movimientoRequest);

    List<Movimiento> getAllMovimiento();

    Movimiento getMovimiento(long idMovimiento);

    void deleteMovimiento(long idMovimiento);
}
