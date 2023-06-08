package com.cristianpenaranda.apiRestDevsu.controller;

import com.cristianpenaranda.apiRestDevsu.entity.Movimiento;
import com.cristianpenaranda.apiRestDevsu.service.MovimientoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cristian Peñaranda
 **/
@RestController
@RequestMapping("movimiento")
public class MovimientoController {

    public static Log LOG = LogFactory.getLog(Movimiento.class);
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> saveMovimiento(@RequestBody Movimiento movimientoRequest) {
        Map<String,Object> map = new HashMap<>();
        try {
            movimientoRequest = movimientoService.saveMovimiento(movimientoRequest);
            map.put("data",movimientoRequest);
            map.put("message","El movimiento se registró exisotsamente.");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (HibernateException e) {
            LOG.error("Error al guardar movimiento: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (ResponseStatusException r){
            map.put("error", r.getReason());
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @PutMapping
    public ResponseEntity<Map<String,Object>>  updateMovimiento(HttpServletRequest request, @RequestBody Movimiento movimientoRequest) {
        Map<String,Object> map = new HashMap<>();
        try {
            //movimientoRequest = movimientoService.updateMovimiento(movimientoRequest);
            map.put("message", "No se puede actualizar el movimiento.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e ) {
            LOG.error("Error: " + e.getMessage( ));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<Movimiento>> getAllMovimiento() {
        List<Movimiento> listMovimientoReturn = null;
        try {
            listMovimientoReturn = movimientoService.getAllMovimiento();
            return new ResponseEntity<>(listMovimientoReturn, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(e.getMessage(), e.getCause());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            return new ResponseEntity<>(null, r.getStatus());
        }
    }

    @GetMapping(value = "/{idMovimiento}")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getMovimientoById(@PathVariable Long idMovimiento) {
        Map<String,Object> map = new HashMap<>();
        Movimiento movimientoRequest = null;
        try {
            movimientoRequest = movimientoService.getMovimiento(idMovimiento);
            map.put("data", movimientoRequest);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(" Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","El movimiento solicitado no está registrado.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @DeleteMapping("/{idMovimiento}")
    public ResponseEntity<Map<String,Object>> deleteMovimiento(HttpServletRequest request, @PathVariable Long idMovimiento) {
        Map<String,Object> map = new HashMap<>();
        try {
            Movimiento movimientoRequest = movimientoService.getMovimiento(idMovimiento);
            movimientoService.deleteMovimiento(idMovimiento);
            map.put("message", "El movimiento se eliminó correctamente.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error", "No existe el movimiento solicitado.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }
}
