package com.ceiba.prestamo.modelo.entidad;


import lombok.Getter;


import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.*;

@Getter
public class Prestamo {

    private static final String SE_DEBE_INGRESAR_LA_FECHA_CREACION = "Se debe ingresar la fecha de creación";
    private static final String SE_DEBE_INGRESAR_EQUIPO_COMPUTO = "Se debe registrar el equipo a solicitar";
    private static final String SE_DEBE_INGRESAR_LA_CEDULA_DE_USUARIO = "Se debe ingresar la cedula de usuario";
    private static final String LA_CEDULA_ES_NUMERICA = "la cedula es de numerica";


    private Long id;
    private Long cedula;
    private String equipoComputo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private int estado;

    public Prestamo(Long id,Long cedula, String equipoComputo, LocalDate fechaCreacion,
                    LocalDate fechaEntrega ,int estado) {

        validarObligatorio(cedula, SE_DEBE_INGRESAR_LA_CEDULA_DE_USUARIO);
        validarObligatorio(equipoComputo, SE_DEBE_INGRESAR_EQUIPO_COMPUTO);
        validarObligatorio(fechaCreacion, SE_DEBE_INGRESAR_LA_FECHA_CREACION);

        this.id = id;
        this.cedula = cedula;
        this.equipoComputo = equipoComputo;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

}
