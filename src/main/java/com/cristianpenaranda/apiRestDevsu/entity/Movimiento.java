package com.cristianpenaranda.apiRestDevsu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Cristian Peñaranda
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimiento", schema = "public")
public class Movimiento {
    @Id
    @Column(name = "movi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @Column(name = "movi_fecha")
    private LocalDateTime fecha;

    @Column(name = "movi_tipoMovimiento")
    private String tipoMovimiento;

    @Column(name = "movi_valor")
    private String valor;

    @Column(name = "movi_saldo")
    private String saldo;

    @OneToOne
    @JoinColumn(name = "cuen_id")
    private Cuenta cuenta;
}
