package com.ceiba.prestamo.consulta;

import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import org.springframework.stereotype.Component;


@Component
public class ManejadorListarPrestamo {

    private final DaoPrestamo daoPrestamo;

    public ManejadorListarPrestamo(DaoPrestamo daoPrestamo){
        this.daoPrestamo = daoPrestamo;
    }

    public DtoPrestamo ejecutar(Long cedula){ return this.daoPrestamo.solicitudCreada(cedula); }
}
