package com.ceiba.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import com.ceiba.prestamo.servicio.ServicioActualizarPrestamo;
import com.ceiba.prestamo.servicio.ServicioCrearPrestamo;
import com.ceiba.servicio.testdatabuilder.PrestamoTestDataBuilder;
import com.ceiba.suspension.modelo.entidad.Suspension;
import com.ceiba.suspension.puerto.dao.DaoSuspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import com.ceiba.usuario.puerto.repositorio.RepositorioUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.calcularDiasPorFecha;
import static com.ceiba.dominio.ValidadorArgumento.sumaFecha;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class ServicioActualizarPrestamoTest {

    @Test
    @DisplayName("Deberia validar existencia de prestamo")
    void deberiaValidarLaExistenciaPrestamo() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035l).build();
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(false);
        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioActualizarPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "La solicitud no existe en el sistema");

    }

    @Test
    @DisplayName("Deberia validar el estado del prestamo")
    void deberiaValidarElEstadoDelPrestamo() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035l).conEstado(3).build();
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioActualizarPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "El estado enviado no corresponde al posible a actualizar");
    }

    @Test
    @DisplayName("Deberia actualizar prestamo sin suspension")
    void deberiaActualizarElPrestamoSinSuspension() {
        // arrange
        LocalDate fechaEntrega = sumaFecha(10,LocalDate.now());
        Prestamo prestamo = new PrestamoTestDataBuilder().conId(1L).conCedula(1023009035l).conEstado(0)
                .conFechaCreacion(LocalDate.now()).conFechaEntrega(fechaEntrega).build();

        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(repositorioPrestamo).actualizar(prestamo);

        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        String respuesta =servicioActualizarPrestamo.ejecutar(prestamo);

        //- assert
        assertEquals("La solicitud fue actualizada con exito" ,respuesta);
    }

    @Test
    @DisplayName("Deberia actualizar prestamo con suspension rol estudiante")
    void deberiaActualizarElPrestamoConSuspensionEstudiante() {
        // arrange
        LocalDate fechaFinSuspension = sumaFecha(20,LocalDate.now());
        Prestamo prestamo = new PrestamoTestDataBuilder().conId(1L).conCedula(1023009035l).conEstado(2)
                .conFechaCreacion(LocalDate.parse("2022-01-10")).conFechaEntrega(LocalDate.parse("2022-01-24")).build();

        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(1);
        Mockito.doNothing().when(repositorioPrestamo).actualizar(prestamo);

        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        String respuesta =servicioActualizarPrestamo.ejecutar(prestamo);

        //- assert
        assertEquals("La solicitud fue actualizada " +
                "con exito, la persona queda suspendida hasta: " + fechaFinSuspension,respuesta);
    }

    @Test
    @DisplayName("Deberia actualizar prestamo con suspension rol administrador")
    void deberiaActualizarElPrestamoConSuspensionAdministrador() {
        // arrange
        LocalDate fechaFinSuspension = sumaFecha(30,LocalDate.now());
        Prestamo prestamo = new PrestamoTestDataBuilder().conId(1L).conCedula(1023009035l).conEstado(2)
                .conFechaCreacion(LocalDate.parse("2022-01-10")).conFechaEntrega(LocalDate.parse("2022-01-24")).build();

        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(2);
        Mockito.doNothing().when(repositorioPrestamo).actualizar(prestamo);

        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        String respuesta =servicioActualizarPrestamo.ejecutar(prestamo);

        //- assert
        assertEquals("La solicitud fue actualizada " +
                "con exito, la persona queda suspendida hasta: " + fechaFinSuspension,respuesta);
    }

    @Test
    @DisplayName("Deberia crear suspension prestamo rol administrador")
    void deberiaCrearSuspensionAdministrador() {
        // arrange
        LocalDate fechaFinSuspension = sumaFecha(30,LocalDate.now());
        Prestamo prestamo = new PrestamoTestDataBuilder().conId(1L).conCedula(1023009035l).conEstado(2)
                .conFechaCreacion(LocalDate.parse("2022-01-10")).conFechaEntrega(LocalDate.parse("2022-01-24")).build();
        Long valorPago = calcularDiasPorFecha(prestamo.getFechaEntrega(),LocalDate.now());
        Suspension suspension = new Suspension(2L,1023009035l,LocalDate.now(),fechaFinSuspension,valorPago);

        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);

        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(2);
        Mockito.when(repositorioSuspension.crear(suspension)).thenReturn(2L);
        Mockito.doNothing().when(repositorioPrestamo).actualizar(prestamo);


        ServicioActualizarPrestamo servicioActualizarPrestamo = new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario,
                repositorioSuspension);

        // act - assert
        String respuesta =servicioActualizarPrestamo.ejecutar(prestamo);

        //- assert
        Mockito.verify(repositorioPrestamo,times(1)).actualizar(prestamo);
    }
}