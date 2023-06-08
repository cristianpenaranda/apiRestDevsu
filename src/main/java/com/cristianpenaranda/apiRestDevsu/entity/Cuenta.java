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
@Table(name = "cuenta", schema = "public")
public class Cuenta {
    @Id
    @Column(name = "cuen_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Column(name = "cuen_numeroCuenta")
    private String numeroCuenta;

    @Column(name = "cuen_tipoCuenta")
    private String tipoCuenta;

    @Column(name = "cuen_saldoInicial")
    private String saldoInicial;

    @Column(name = "cuen_estado")
    private String estado;

    @OneToOne
    @JoinColumn(name = "clie_id")
    private Cliente cliente;
}
