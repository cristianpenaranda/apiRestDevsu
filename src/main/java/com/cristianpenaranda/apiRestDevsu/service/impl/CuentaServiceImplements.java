package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import com.cristianpenaranda.apiRestDevsu.repository.ClienteRepository;
import com.cristianpenaranda.apiRestDevsu.repository.CuentaRepository;
import com.cristianpenaranda.apiRestDevsu.service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristian Peñaranda
 **/
@Service
public class CuentaServiceImplements implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    public CuentaServiceImplements(CuentaRepository cuentaRepository,
                                   ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cuenta saveCuenta(Cuenta cuentaRequest) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(cuentaRequest.getNumeroCuenta());
        if(cuenta != null){
            throw new ResponseStatusException(HttpStatus.IM_USED);
        }else{
            Optional<Cliente> cliente = clienteRepository.findById(cuentaRequest.getCliente().getIdCliente());
            if(cliente.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }else {
                cuentaRequest.setEstado("True");
            }
        }
        return cuentaRepository.save(cuentaRequest);
    }

    @Override
    @Transactional
    public Cuenta updateCuenta(Cuenta cuentaRequest) {
        Optional<Cuenta> cuentaAux = cuentaRepository.findById(cuentaRequest.getIdCuenta());
        if(cuentaAux.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            cuentaRequest.setEstado("True");
            return cuentaRepository.save(cuentaRequest);
        }
    }

    @Override
    public List<Cuenta> getAllCuenta() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta getCuenta(long idCuenta) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(idCuenta);
        if(cuenta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return cuenta.get();
    }

    @Override
    public Cuenta getCuentaByNumeroCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if(cuenta == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return cuenta;
    }

    @Override
    @Transactional
    public void deleteCuenta(long idCuenta) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(idCuenta);
        if(cuenta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cuentaRepository.deleteById(idCuenta);
    }
}
