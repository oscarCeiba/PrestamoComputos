package com.ceiba.prestamo.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComandoPrestamo {
    private Long id;
    private Long cedula;
    private String equipoComputo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private int estado;
}
