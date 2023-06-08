package com.cristianpenaranda.apiRestDevsu.repository;

import com.cristianpenaranda.apiRestDevsu.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cristian Peñaranda
 **/
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
