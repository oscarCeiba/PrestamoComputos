package com.ceiba.entidad;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.servicio.testdatabuilder.PrestamoTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrestamoTest {

    @Test
    @DisplayName("Deberia crear correctamente el prestamo")
    void deberiaCrearCorrectamenteElPrestamo() {
        // arrange
        LocalDate fechaCreacion = LocalDate.now();
        Long cedula = 1023009035L;
        String equipoComputo = "Asus1";
        //act
        Prestamo prestamo = new PrestamoTestDataBuilder().conFechaCreacion(fechaCreacion).conCedula(cedula)
                .conEquipoComputo(equipoComputo).build();
        //assert
        assertEquals(fechaCreacion, prestamo.getFechaCreacion());
    }

    @Test
    void deberiaFallarSinCedula() {

        //Arrange
        PrestamoTestDataBuilder PrestamoTestDataBuilder = new PrestamoTestDataBuilder().conCedula(null);
        //act-assert
        BasePrueba.assertThrows(() -> {
                    PrestamoTestDataBuilder.build();
                },
                ExcepcionValorObligatorio.class, "Se debe ingresar la cedula de usuario");
    }


    @Test
    void deberiaFallarSinFechaCreacion() {

        //Arrange
        PrestamoTestDataBuilder usuarioTestDataBuilder = new PrestamoTestDataBuilder().conFechaCreacion(null)
                .conCedula(1023009035L).conEquipoComputo("Asus1");
        //act-assert
        BasePrueba.assertThrows(() -> {
                    usuarioTestDataBuilder.build();
                },
                ExcepcionValorObligatorio.class, "Se debe ingresar la fecha de creación");
    }


}
