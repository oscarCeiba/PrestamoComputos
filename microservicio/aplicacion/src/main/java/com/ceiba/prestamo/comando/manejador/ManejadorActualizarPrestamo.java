package com.ceiba.prestamo.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.manejador.ManejadorComando;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import com.ceiba.prestamo.comando.ComandoPrestamo;
import com.ceiba.prestamo.comando.fabrica.FabricaPrestamo;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.servicio.ServicioActualizarPrestamo;
import org.springframework.stereotype.Component;

@Component
public class ManejadorActualizarPrestamo implements ManejadorComandoRespuesta<ComandoPrestamo, ComandoRespuesta<String>> {

    private final FabricaPrestamo fabricaPrestamo;
    private final ServicioActualizarPrestamo servicioActualizarPrestamo;

    public ManejadorActualizarPrestamo(FabricaPrestamo fabricaPrestamo, ServicioActualizarPrestamo servicioActualizarPrestamo) {
        this.fabricaPrestamo = fabricaPrestamo;
        this.servicioActualizarPrestamo = servicioActualizarPrestamo;
    }

    public ComandoRespuesta<String> ejecutar(ComandoPrestamo comandoPrestamo) {
        Prestamo prestamo = this.fabricaPrestamo.crear(comandoPrestamo);
        return new ComandoRespuesta<>(this.servicioActualizarPrestamo.ejecutar(prestamo));
    }
}
