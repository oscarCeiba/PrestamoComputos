package com.ceiba.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import com.ceiba.prestamo.servicio.ServicioCrearPrestamo;
import com.ceiba.servicio.testdatabuilder.PrestamoTestDataBuilder;
import com.ceiba.suspension.modelo.dto.DtoSuspension;
import com.ceiba.suspension.puerto.dao.DaoSuspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import com.ceiba.usuario.puerto.repositorio.RepositorioUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class ServicioCrearPrestamoTest {

    @Test
    @DisplayName("Deberia validar la existencia del usuario")
    void deberiaValidarLaExistenciaUsuario() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1111l).build();
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);
        Mockito.when(repositorioUsuario.existe(Mockito.anyLong())).thenReturn(false);
        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "El usuario no existe en el sistema");

    }

    @Test
    @DisplayName("Deberia validar la existencia del Prestamo")
    void deberiaValidarLaExistenciaDePrestamo() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035L).build();
        DtoPrestamo dtoPrestamo = new DtoPrestamo(1L,1023009035L,"asus",
                LocalDate.now(),LocalDate.parse("2022-02-08"),1);
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);

        Mockito.when(repositorioUsuario.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(daoPrestamo.solicitudCreada(Mockito.anyLong())).thenReturn(dtoPrestamo);
        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "El usuario tiene una solicitud activa");

    }

    @Test
    @DisplayName("Deberia validar la existencia del Prestamo suspendido en este caso Activo")
    void deberiaValidarLaExistenciaDePrestamoSuspendidoActivo() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035L).build();
        DtoPrestamo dtoPrestamo = new DtoPrestamo(1L,1023009035L,"asus",
                LocalDate.parse("2022-01-05"),LocalDate.parse("2022-01-19"),2);
        DtoSuspension dtoSuspension = new DtoSuspension(1L,1023009035L,
                LocalDate.parse("2022-01-20"),LocalDate.parse("2022-02-16"),55500L);
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);

        Mockito.when(repositorioUsuario.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(daoPrestamo.solicitudCreada(Mockito.anyLong())).thenReturn(dtoPrestamo);
        Mockito.when(daoSuspension.solicitudCreada(Mockito.anyLong())).thenReturn(dtoSuspension);

        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "El usuario tiene una solicitud suspendida");

    }

    @Test
    @DisplayName("Deberia validar la existencia del Prestamo suspendido pero por inactivo ya dejaria crear")
    void deberiaValidarLaExistenciaDePrestamoSuspendidoInactivo() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035L).build();
        DtoPrestamo dtoPrestamo = new DtoPrestamo(1L,1023009035L,"asus",
                LocalDate.parse("2021-11-01"),LocalDate.parse("2021-11-15"),2);
        DtoSuspension dtoSuspension = new DtoSuspension(1L,1023009035L,
                LocalDate.parse("2021-11-16"),LocalDate.parse("2021-12-14"),55500L);
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);

        Mockito.when(repositorioUsuario.existe(1023009035L)).thenReturn(true);
        Mockito.when(repositorioPrestamo.existe(1023009035L)).thenReturn(true);
        Mockito.when(daoPrestamo.solicitudCreada(Mockito.anyLong())).thenReturn(dtoPrestamo);
        Mockito.when(daoSuspension.solicitudCreada(Mockito.anyLong())).thenReturn(dtoSuspension);
        Mockito.doNothing().when(repositorioPrestamo).actualizarEstado(1023009035L,0);
        Mockito.doNothing().when(repositorioSuspension).eliminar(Mockito.anyLong());
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(1);

        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        String respuesta =servicioCrearPrestamo.ejecutar(prestamo);

        //- assert
        Mockito.verify(repositorioPrestamo,times(1)).actualizarEstado(Mockito.anyLong()
                ,Mockito.anyInt());
        Mockito.verify(repositorioSuspension,times(1)).eliminar(Mockito.anyLong());
        assertEquals("La solicitud fue realizada con exito, la fecha maxima de entrega es: "
                + "2022-02-08" ,respuesta);

    }

    @Test
    @DisplayName("Deberia validar el rol diferente a estudiante y administrador")
    void deberiaValidarRolDiferenteALosPermitidos() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035L).build();
        DtoPrestamo dtoPrestamo = new DtoPrestamo(1L,1023009035L,"asus",
                LocalDate.now(),LocalDate.parse("2022-02-08"),1);
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);

        Mockito.when(repositorioUsuario.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(false);
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(3);

        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearPrestamo.ejecutar(prestamo), ExcepcionValorInvalido.class,
                "El usuario tiene un rol diferente al permitido para la solicitud");

    }

    @Test
    @DisplayName("crear Prestamo rol de administrador")
    void deberiaCrearPrestamoRoladministrador() {
        // arrange
        Prestamo prestamo = new PrestamoTestDataBuilder().conCedula(1023009035L).build();
        DtoPrestamo dtoPrestamo = new DtoPrestamo(1L,1023009035L,"asus",
                LocalDate.now(),LocalDate.parse("2022-02-08"),1);
        RepositorioPrestamo repositorioPrestamo = Mockito.mock(RepositorioPrestamo.class);
        RepositorioUsuario repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        RepositorioSuspension repositorioSuspension = Mockito.mock(RepositorioSuspension.class);
        DaoPrestamo daoPrestamo = Mockito.mock(DaoPrestamo.class);
        DaoSuspension daoSuspension = Mockito.mock(DaoSuspension.class);

        Mockito.when(repositorioUsuario.existe(Mockito.anyLong())).thenReturn(true);
        Mockito.when(repositorioPrestamo.existe(Mockito.anyLong())).thenReturn(false);
        Mockito.when(repositorioUsuario.rolUsuario(Mockito.anyLong())).thenReturn(2);

        ServicioCrearPrestamo servicioCrearPrestamo = new ServicioCrearPrestamo(repositorioPrestamo,repositorioUsuario,
                daoPrestamo, daoSuspension, repositorioSuspension);

        // act - assert
        String respuesta =servicioCrearPrestamo.ejecutar(prestamo);

        //- assert
        assertEquals("La solicitud fue realizada con exito, la fecha maxima de entrega es: "
                + "2022-02-15" ,respuesta);

    }
}