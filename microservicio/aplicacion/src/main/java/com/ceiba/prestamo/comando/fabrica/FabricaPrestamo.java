package com.ceiba.prestamo.comando.fabrica;

import com.ceiba.prestamo.comando.ComandoPrestamo;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import org.springframework.stereotype.Component;


@Component
public class FabricaPrestamo {

    public Prestamo crear(ComandoPrestamo comandoPrestamo) {
        return new Prestamo(
                comandoPrestamo.getId(),
                comandoPrestamo.getCedula(),
                comandoPrestamo.getEquipoComputo(),
                comandoPrestamo.getFechaCreacion(),
                comandoPrestamo.getFechaEntrega(),
                comandoPrestamo.getEstado()
        );
    }

}
