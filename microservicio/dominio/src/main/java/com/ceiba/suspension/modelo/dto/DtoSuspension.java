package com.ceiba.suspension.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DtoSuspension {
    private Long id;
    private Long cedula;
    private LocalDate fechaSuspension;
    private LocalDate fechaFinSuspension;
    private Long pagoRealizado;

}

