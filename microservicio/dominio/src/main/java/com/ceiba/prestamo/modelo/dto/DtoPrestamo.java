package com.ceiba.prestamo.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DtoPrestamo {
    private Long id;
    private Long cedula;
    private String equipoComputo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private int estado;
}

