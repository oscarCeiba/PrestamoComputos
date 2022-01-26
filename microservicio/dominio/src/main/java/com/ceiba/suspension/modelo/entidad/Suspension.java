package com.ceiba.suspension.modelo.entidad;


import lombok.Getter;

import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.*;

@Getter
public class Suspension {

    private static final String SE_DEBE_INGRESAR_LA_FECHA_CREACION = "Se debe ingresar la fecha de creación";
    private static final String SE_DEBE_INGRESAR_LA_CEDULA_DE_USUARIO = "Se debe ingresar la cedula de usuario";
    private static final String LA_CEDULA_ES_NUMERICA = "la cedula es de numerica";
    private static final String LA_DEBE_INGRESAR_LA_FECHA_FIN_SUSPENSION = "la cedula es de numerica";
    private static final String SE_DEBE_INGRESAR_EL_PAGO_REALIZADO = "El pago realizado debe ingresarse";


    private static final int LONGITUD_MINIMA_CLAVE = 4;

    private Long id;
    private Long cedula;
    private LocalDate fechaSuspension;
    private LocalDate fechaFinSuspension;
    private Long pagoRealizado;

    public Suspension(Long id, Long cedula, LocalDate fechaSuspension, LocalDate fechaFinSuspension ,
                      Long pagoRealizado) {

        validarObligatorio(cedula, SE_DEBE_INGRESAR_LA_CEDULA_DE_USUARIO);
        validarNumerico(String.valueOf(cedula), LA_CEDULA_ES_NUMERICA);
        validarObligatorio(fechaSuspension, SE_DEBE_INGRESAR_LA_FECHA_CREACION);
        validarObligatorio(fechaFinSuspension, SE_DEBE_INGRESAR_LA_FECHA_CREACION);
        validarObligatorio(pagoRealizado, SE_DEBE_INGRESAR_LA_FECHA_CREACION);

        this.id = id;
        this.cedula = cedula;
        this.fechaSuspension = fechaSuspension;
        this.fechaFinSuspension = fechaFinSuspension;
        this.pagoRealizado = pagoRealizado;
    }

}
