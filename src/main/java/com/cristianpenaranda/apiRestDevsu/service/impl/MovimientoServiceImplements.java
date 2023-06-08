package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Cuenta;
import com.cristianpenaranda.apiRestDevsu.entity.Movimiento;
import com.cristianpenaranda.apiRestDevsu.repository.ClienteRepository;
import com.cristianpenaranda.apiRestDevsu.repository.CuentaRepository;
import com.cristianpenaranda.apiRestDevsu.repository.MovimientoRepository;
import com.cristianpenaranda.apiRestDevsu.service.MovimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristian Peñaranda
 **/
@Service
public class MovimientoServiceImplements implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    private final double limiteDiarioRetiro = 1000;

    public MovimientoServiceImplements(MovimientoRepository movimientoRepository,
                                       CuentaRepository cuentaRepository,
                                       ClienteRepository clienteRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Movimiento saveMovimiento(Movimiento movimientoRequest) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(movimientoRequest.getCuenta().getIdCuenta());
        if(cuenta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta solicitada no está registrada.");
        }else{
            Optional<Cliente> cliente = clienteRepository.findById(movimientoRequest.getCuenta().getCliente().getIdCliente());
            if(cliente.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente solicitado no está registrado.");
            }else{
                double valorMov = Double.parseDouble(movimientoRequest.getValor());
                String query = movimientoRepository.findSaldoActual(cliente.get().getIdCliente(), cuenta.get().getIdCuenta());
                double saldoCuenta = query!=null ? Double.parseDouble(query) : Double.parseDouble(cuenta.get().getSaldoInicial());
                if(saldoCuenta == 0){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Saldo no disponible");
                }else if(saldoCuenta < valorMov){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tiene saldo suficiente para realizar el débito");
                }else if(this.validarLimiteDiarioRetiros(valorMov, cliente.get().getIdCliente(), cuenta.get().getIdCuenta())){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupo diario excedido");
                }else{
                    movimientoRequest.setTipoMovimiento(valorMov>0 ? "CREDITO" : "DEBITO");
                    double nuevoSaldo = 0;
                    if (valorMov>0) {
                        nuevoSaldo = saldoCuenta + valorMov;
                    }else{
                        nuevoSaldo = saldoCuenta - Math.abs(valorMov);
                    }
                    movimientoRequest.setSaldo(String.valueOf(nuevoSaldo));
                    movimientoRequest.setFecha(LocalDateTime.now());
                }
                return movimientoRepository.save(movimientoRequest);
            }
        }
    }

    private boolean validarLimiteDiarioRetiros(double valorMov, long idCliente, long idCuenta){
        List<Double> findMovimientoDelDia = movimientoRepository.findMovimientoDelDia(idCliente, idCuenta);
        double saldoDelDia = 0;
        if(findMovimientoDelDia != null && findMovimientoDelDia.size()>0){
            for(Double aux : findMovimientoDelDia){
                saldoDelDia += aux;
            }
        }
        if(Math.abs(saldoDelDia) >= limiteDiarioRetiro){
            return true;
        }else if(valorMov<0 && (Math.abs(saldoDelDia)+Math.abs(valorMov)) > limiteDiarioRetiro){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Movimiento updateMovimiento(Movimiento movimientoRequest) {

        return movimientoRepository.save(movimientoRequest);
    }

    @Override
    public List<Movimiento> getAllMovimiento() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento getMovimiento(long idMovimiento) {
        Optional<Movimiento> movimiento = movimientoRepository.findById(idMovimiento);
        if(movimiento.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return movimiento.get();
    }

    @Override
    @Transactional
    public void deleteMovimiento(long idMovimiento) {
        Optional<Movimiento> movimiento = movimientoRepository.findById(idMovimiento);
        if(movimiento.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el movimiento solicitado.");
        }
        movimientoRepository.deleteById(idMovimiento);
    }
}
