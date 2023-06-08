package com.cristianpenaranda.apiRestDevsu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Cristian Peñaranda
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persona", schema = "public")
public class Persona {
    @Id
    @Column(name = "pers_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(name = "pers_nombre")
    private String nombre;

    @Column(name = "pers_genero")
    private String genero;

    @Column(name = "pers_edad")
    private int edad;

    @Column(name = "pers_identificacion")
    private String identificacion;

    @Column(name = "pers_direccion")
    private String direccion;

    @Column(name = "pers_telefono")
    private String telefono;
}
