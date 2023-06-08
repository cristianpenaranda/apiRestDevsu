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
@Table(name = "cliente", schema = "public")
public class Cliente{
    @Id
    @Column(name = "clie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(name = "clie_contrasena")
    private String contrasena;

    @Column(name = "clie_estado")
    private String estado;

    @OneToOne
    @JoinColumn(name = "pers_id")
    private Persona persona;
}
