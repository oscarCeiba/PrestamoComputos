package com.ceiba.prestamo.servicio;

import com.ceiba.prestamo.comando.ComandoPrestamo;

import java.time.LocalDate;

public class ComandoPrestamoTestDataBuilder {

    private Long id;
    private Long cedula;
    private String equipoComputo;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private int estado;

    public ComandoPrestamoTestDataBuilder() {
        equipoComputo = "Asus1";
        fechaCreacion = LocalDate.now();
    }

    public ComandoPrestamoTestDataBuilder conId(Long id) {
        this.id = id;
        return this;
    }

    public ComandoPrestamoTestDataBuilder conCedula(Long cedula) {
        this.cedula = cedula;
        return this;
    }

    public ComandoPrestamoTestDataBuilder conEquipoComputo(String equipoComputo) {
        this.equipoComputo = equipoComputo;
        return this;
    }

    public ComandoPrestamoTestDataBuilder conFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public ComandoPrestamoTestDataBuilder conFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public ComandoPrestamoTestDataBuilder conEstado(int estado) {
        this.estado = estado;
        return this;
    }

    public ComandoPrestamo build() {
        return new ComandoPrestamo(id,cedula, equipoComputo,fechaCreacion,fechaEntrega,estado);
    }
}
