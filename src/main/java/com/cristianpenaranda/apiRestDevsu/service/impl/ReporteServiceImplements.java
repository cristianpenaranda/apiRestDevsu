package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.repository.ClienteRepository;
import com.cristianpenaranda.apiRestDevsu.repository.MovimientoRepository;
import com.cristianpenaranda.apiRestDevsu.service.ReporteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * @author Cristian Peñaranda
 **/
@Service
public class ReporteServiceImplements implements ReporteService {
    private final MovimientoRepository movimientoRepository;
    private final ClienteRepository clienteRepository;

    public ReporteServiceImplements(MovimientoRepository movimientoRepository,
                                    ClienteRepository clienteRepository) {
        this.movimientoRepository = movimientoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Map<String,Object>> getReporteByIdClienteAndFechas(Long idCliente, Date fechaInicial, Date fechaFinal){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente solicitado no está registrado.");
        }
        List<Map<String,Object>> listaReporte = movimientoRepository.findByIdCliente(idCliente, fechaInicial, fechaFinal);
        if(listaReporte == null || listaReporte.size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay resultados.");
        }
        return listaReporte;
    }

}
