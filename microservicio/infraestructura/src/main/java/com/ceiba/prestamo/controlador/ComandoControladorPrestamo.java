package com.ceiba.prestamo.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.prestamo.comando.ComandoPrestamo;
import com.ceiba.prestamo.comando.manejador.ManejadorActualizarPrestamo;
import com.ceiba.prestamo.comando.manejador.ManejadorCrearPrestamo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamo")
@Api(tags = { "Controlador comando prestamo"})
public class ComandoControladorPrestamo {

    private final ManejadorCrearPrestamo manejadorCrearPrestamo;
	private final ManejadorActualizarPrestamo manejadorActualizarPrestamo;

    @Autowired
    public ComandoControladorPrestamo(ManejadorCrearPrestamo manejadorCrearPrestamo,
									  ManejadorActualizarPrestamo manejadorActualizarPrestamo) {
        this.manejadorCrearPrestamo = manejadorCrearPrestamo;
		this.manejadorActualizarPrestamo = manejadorActualizarPrestamo;
    }

    @PostMapping
    @ApiOperation("Crear Prestamo")
    public ComandoRespuesta<String> crear(@RequestBody ComandoPrestamo comandoPrestamo) {
        return manejadorCrearPrestamo.ejecutar(comandoPrestamo);
    }

	@PutMapping
	@ApiOperation("Actualizar Prestamo")
	public ComandoRespuesta<String> actualizar(@RequestBody ComandoPrestamo comandoPrestamo) {
		return manejadorActualizarPrestamo.ejecutar(comandoPrestamo);
	}
}
