package com.cristianpenaranda.apiRestDevsu.controller;

import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import com.cristianpenaranda.apiRestDevsu.service.CuentaService;
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
@RequestMapping("cuenta")
public class CuentaController {

    public static Log LOG = LogFactory.getLog(Cuenta.class);
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> saveCuenta(@RequestBody Cuenta cuentaRequest) {
        Map<String,Object> map = new HashMap<>();
        try {
            cuentaRequest = cuentaService.saveCuenta(cuentaRequest);
            map.put("data",cuentaRequest);
            map.put("message","La cuenta se registró exisotsamente.");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (HibernateException e) {
            LOG.error("Error al guardar cuenta: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            if(r.getStatus() == HttpStatus.IM_USED){
                map.put("error","La cuenta con el número "+cuentaRequest.getNumeroCuenta()+" ya está registrada.");
            }else if(r.getStatus() == HttpStatus.NOT_FOUND){
                map.put("error","El cliente solicitado no está registrado.");
            }
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @PutMapping
    public ResponseEntity<Map<String,Object>> updateCuenta(HttpServletRequest request, @RequestBody Cuenta cuentaRequest) {
        Map<String,Object> map = new HashMap<>();
        try {
            cuentaRequest = cuentaService.updateCuenta(cuentaRequest);
            map.put("data",cuentaRequest);
            map.put("message","La cuenta se modificó exisotsamente.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e ) {
            LOG.error("Error: " + e.getMessage( ));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            if(r.getStatus() == HttpStatus.NOT_FOUND){
                map.put("error","La cuenta solicitada no está registrada.");
            }
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<Cuenta>> getAllCuenta() {
        List<Cuenta> listCuentaReturn = null;
        try {
            listCuentaReturn = cuentaService.getAllCuenta();
            return new ResponseEntity<>(listCuentaReturn, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(e.getMessage(), e.getCause());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{idCuenta}")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getCuentaById(@PathVariable Long idCuenta) {
        Map<String,Object> map = new HashMap<>();
        Cuenta cuentaRequest = null;
        try {
            cuentaRequest = cuentaService.getCuenta(idCuenta);
            map.put("data", cuentaRequest);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(" Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","La cuenta solicitada no está registrada.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @GetMapping(value = "/numerocuenta/{numeroCuenta}")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getCuentaByNumeroCuenta(@PathVariable String numeroCuenta) {
        Map<String,Object> map = new HashMap<>();
        Cuenta cuentaRequest = null;
        try {
            cuentaRequest = cuentaService.getCuentaByNumeroCuenta(numeroCuenta);
            map.put("data", cuentaRequest);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(" Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","La cuenta "+numeroCuenta+" no está registrada.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @DeleteMapping("/{idCuenta}")
    public ResponseEntity<Map<String,Object>> deleteCuenta(HttpServletRequest request, @PathVariable Long idCuenta) {
        Map<String,Object> map = new HashMap<>();
        try {
            Cuenta cuentaRequest = cuentaService.getCuenta(idCuenta);
            cuentaService.deleteCuenta(idCuenta);
            map.put("message", "La cuenta se eliminó correctamente.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","La cuenta solicitada no está registrada.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }
}
