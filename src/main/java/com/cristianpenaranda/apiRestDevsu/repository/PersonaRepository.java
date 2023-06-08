package com.cristianpenaranda.apiRestDevsu.repository;

import com.cristianpenaranda.apiRestDevsu.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Cristian Peñaranda
 **/
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "SELECT * FROM persona pers WHERE pers.pers_identificacion = ?", nativeQuery = true)
    Persona findByIdentificacion(String identificacion);

}
