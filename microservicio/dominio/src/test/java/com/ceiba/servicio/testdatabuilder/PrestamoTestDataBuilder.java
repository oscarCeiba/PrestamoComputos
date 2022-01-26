package com.ceiba.servicio.testdatabuilder;

import com.ceiba.prestamo.modelo.entidad.Prestamo;


import java.time.LocalDate;


public class PrestamoTestDataBuilder {

    private Long id;
    private Long cedula;
    private String equipoComputo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private int estado;

    public PrestamoTestDataBuilder() {
        equipoComputo = "Asus1";
        fechaCreacion = LocalDate.parse("2022-01-25");
    }

    public PrestamoTestDataBuilder conId(Long id) {
        this.id = id;
        return this;
    }

    public PrestamoTestDataBuilder conCedula(Long cedula) {
        this.cedula = cedula;
        return this;
    }

    public PrestamoTestDataBuilder conEquipoComputo(String equipoComputo) {
        this.equipoComputo = equipoComputo;
        return this;
    }

    public PrestamoTestDataBuilder conFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public PrestamoTestDataBuilder conFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public PrestamoTestDataBuilder conEstado(int estado) {
        this.estado = estado;
        return this;
    }

    public Prestamo build() {
        return new Prestamo(id,cedula,equipoComputo,fechaCreacion,fechaEntrega,estado);
    }
}
