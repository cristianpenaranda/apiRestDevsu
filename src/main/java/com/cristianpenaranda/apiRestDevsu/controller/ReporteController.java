package com.cristianpenaranda.apiRestDevsu.controller;

import com.cristianpenaranda.apiRestDevsu.service.ReporteService;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * @author Cristian Peñaranda
 **/
@RestController
@RequestMapping("reporte")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping(value = "/{idCliente}/{fechaInicial}/{fechaFinal}")
    @ResponseBody
    public ResponseEntity<List<Map<String,Object>>> getReporteByIdClienteAndFechas(@PathVariable Long idCliente, @PathVariable String fechaInicial, @PathVariable String fechaFinal) {
        List<Map<String,Object>> listaReporte = new ArrayList<>();
        try {
            Date fechaIni = new Date((Integer.parseInt(fechaInicial.split("-")[0])-1900), (Integer.parseInt(fechaInicial.split("-")[1])-1), Integer.parseInt(fechaInicial.split("-")[2]));
            Date fechaFin = new Date((Integer.parseInt(fechaFinal.split("-")[0])-1900), (Integer.parseInt(fechaFinal.split("-")[1])-1), Integer.parseInt(fechaFinal.split("-")[2]));
            listaReporte = reporteService.getReporteByIdClienteAndFechas(idCliente, fechaIni, fechaFin);
            return new ResponseEntity<>(listaReporte, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            Map<String,Object> map = new HashMap<>();
            map.put("error", r.getReason());
            listaReporte.add(map);
            return new ResponseEntity<>(listaReporte, r.getStatus());
        }
    }
}
