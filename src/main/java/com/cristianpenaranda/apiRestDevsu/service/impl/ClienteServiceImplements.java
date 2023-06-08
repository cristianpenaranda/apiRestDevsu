package com.cristianpenaranda.apiRestDevsu.service.impl;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import com.cristianpenaranda.apiRestDevsu.entity.Persona;
import com.cristianpenaranda.apiRestDevsu.repository.ClienteRepository;
import com.cristianpenaranda.apiRestDevsu.repository.PersonaRepository;
import com.cristianpenaranda.apiRestDevsu.service.ClienteService;
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
public class ClienteServiceImplements implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;

    public ClienteServiceImplements(ClienteRepository clienteRepository,
                                    PersonaRepository personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    @Transactional
    public Cliente saveCliente(Cliente clienteRequest) {
        Persona persona = null;
        if(!clienteRequest.getPersona().getIdentificacion().isEmpty()){
            persona = personaRepository.findByIdentificacion(clienteRequest.getPersona().getIdentificacion());
        }
        if(persona != null){
            throw new ResponseStatusException(HttpStatus.IM_USED);
        }else{
            persona = personaRepository.save(clienteRequest.getPersona());
            clienteRequest.setPersona(persona);
            clienteRequest.setEstado("True");
        }
        return clienteRepository.save(clienteRequest);
    }

    @Override
    @Transactional
    public Cliente updateCliente(Cliente clienteRequest) {
        Optional<Cliente> clienteAux = clienteRepository.findById(clienteRequest.getIdCliente());
        if(clienteAux.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            clienteRequest.getPersona().setIdPersona(clienteAux.get().getPersona().getIdPersona());
            personaRepository.save(clienteRequest.getPersona());
            clienteRequest.setEstado("True");
            return clienteRepository.save(clienteRequest);
        }
    }

    @Override
    public List<Cliente> getAllCliente() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getCliente(long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return cliente.get();
    }

    @Override
    @Transactional
    public void deleteCliente(long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        personaRepository.deleteById(cliente.get().getPersona().getIdPersona());
        clienteRepository.deleteById(idCliente);
    }
}
