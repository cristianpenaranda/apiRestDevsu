package com.cristianpenaranda.apiRestDevsu.service;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Cristian Peñaranda
 **/
public interface ReporteService {

    List<Map<String,Object>> getReporteByIdClienteAndFechas(Long idCliente, Date fechaInicial, Date fechaFinal);
}
