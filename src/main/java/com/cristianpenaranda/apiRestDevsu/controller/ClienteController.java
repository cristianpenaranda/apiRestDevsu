package com.cristianpenaranda.apiRestDevsu.controller;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.service.ClienteService;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cristian Peñaranda
 **/
@RestController
@RequestMapping("cliente")
public class ClienteController {

    public static Log LOG = LogFactory.getLog(Cliente.class);
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCliente(@RequestBody Cliente clienteRequest) {
        Map<String, Object> map = new HashMap<>();
        try {
            clienteRequest = clienteService.saveCliente(clienteRequest);
            map.put("data", clienteRequest);
            map.put("message", "El cliente se registró exitosamente.");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (HibernateException e) {
            LOG.error("Error al guardar cliente: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (ResponseStatusException r){
            map.put("error", "El cliente con el número de identificación "+clienteRequest.getPersona().getIdentificacion()+" ya está registrado.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCliente(HttpServletRequest request, @RequestBody Cliente clienteRequest) {
        Map<String, Object> map = new HashMap<>();
        try {
            clienteRequest = clienteService.updateCliente(clienteRequest);
            map.put("data", clienteRequest);
            map.put("message", "El cliente se modificó exitosamente.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e ) {
            LOG.error("Error: " + e.getMessage( ));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (ResponseStatusException r){
            if(r.getStatus() == HttpStatus.IM_USED){
                map.put("error", "El cliente con el número de identificación "+clienteRequest.getPersona().getIdentificacion()+" ya está registrado.");
            }else if(r.getStatus() == HttpStatus.NOT_FOUND){
                map.put("error","El cliente no existe.");
            }
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<Cliente>> getAllCliente() {
        List<Cliente> listClienteReturn = null;
        try {
            listClienteReturn = clienteService.getAllCliente();
            return new ResponseEntity<>(listClienteReturn, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(e.getMessage(), e.getCause());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            return new ResponseEntity<>(null, r.getStatus());
        }
    }

    @GetMapping(value = "/{idCliente}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getClienteById(@PathVariable Long idCliente) {
        Map<String, Object> map = new HashMap<>();
        Cliente clienteRequest = null;
        try {
            clienteRequest = clienteService.getCliente(idCliente);
            map.put("message", "Cliente encontrado.");
            map.put("data", clienteRequest);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            LOG.info(" Error : " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","El cliente no está registrado.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Map<String, Object>> deleteCliente(HttpServletRequest request, @PathVariable Long idCliente) {
        Map<String, Object> map = new HashMap<>();
        try {
            Cliente clienteRequest = clienteService.getCliente(idCliente);
            clienteService.deleteCliente(idCliente);
            map.put("message", "El cliente se eliminó correctamente.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException r){
            map.put("error","El cliente no está registrado.");
            return new ResponseEntity<>(map, r.getStatus());
        }
    }
}
