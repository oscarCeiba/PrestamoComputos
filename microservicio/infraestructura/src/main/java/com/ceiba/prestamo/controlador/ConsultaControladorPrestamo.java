package com.ceiba.prestamo.controlador;

import com.ceiba.prestamo.consulta.ManejadorListarPrestamo;
import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/prestamo")
@Api(tags={"Controlador consulta prestamo"})
public class ConsultaControladorPrestamo {

    private final ManejadorListarPrestamo manejadorListarPrestamo;

    public ConsultaControladorPrestamo(ManejadorListarPrestamo manejadorListarPrestamo) {
        this.manejadorListarPrestamo = manejadorListarPrestamo;
    }

    @GetMapping(path = "/{cedula}")
    @ApiOperation("consulta solicitud")
    public DtoPrestamo consultarSolicitud(@PathVariable Long cedula) {
        return this.manejadorListarPrestamo.ejecutar(cedula);
    }

}
