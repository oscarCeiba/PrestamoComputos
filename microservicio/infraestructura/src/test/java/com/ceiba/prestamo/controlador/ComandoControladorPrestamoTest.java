package com.ceiba.prestamo.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.prestamo.comando.ComandoPrestamo;
import com.ceiba.prestamo.servicio.ComandoPrestamoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ComandoControladorPrestamo.class)
@ContextConfiguration(classes= ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComandoControladorPrestamoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deberia crear una solicitud de prestamo")
    void deberiaCrearUnaSolicitudPrestamo() throws Exception{
        //arrange
        ComandoPrestamo prestamo = new ComandoPrestamoTestDataBuilder()
                .conCedula(1023009035L).build();
        LocalDate fechaEntrega = sumaFecha(10,prestamo.getFechaCreacion());
        //act - assert
        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor'='La solicitud fue realizada con exito, la fecha maxima de" +
                        " entrega es: "+fechaEntrega+"'}"));
    }

    @Test
    @DisplayName("Deberia crear una solicitud de prestamo a usuario que supero suspension")
    void deberiaCrearUnaSolicitudPrestamoUsuarioSuperoSuspension() throws Exception{
        //arrange
        ComandoPrestamo prestamo = new ComandoPrestamoTestDataBuilder()
                .conCedula(1023009044L).build();

        LocalDate fechaEntrega = sumaFecha(10,prestamo.getFechaCreacion());

        //act - assert
        mockMvc.perform(post("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor'='La solicitud fue realizada con exito, la fecha maxima de" +
                        " entrega es: "+fechaEntrega+"'}"));
    }


    @Test
    @DisplayName("Deberia actualizar una solicitud de prestamo existente no suspension")
    void dberiaActualizarSolicitudPrestamo() throws Exception {
        //arrange
        LocalDate fechaEntrega = sumaFecha(10,LocalDate.now());
        ComandoPrestamo prestamo = new ComandoPrestamoTestDataBuilder()
                .conId(4L).conCedula(1023009037l).conEstado(0)
                .conFechaEntrega(fechaEntrega).build();

        //act - assert
        mockMvc.perform(put("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor'='La solicitud fue actualizada con exito'}"));
    }

    @Test
    @DisplayName("Deberia actualizar una solicitud de prestamo existente con suspension")
    void dberiaActualizarSolicitudPrestamoSuspension() throws Exception {
        //arrange
        LocalDate fechaSuspension = sumaFecha(20,LocalDate.now());
        Long dias = calcularDiasPorFecha(LocalDate.parse("2022-01-24"),LocalDate.now());
        Long multa = dias * 55500;
        ComandoPrestamo prestamo = new ComandoPrestamoTestDataBuilder()
                .conId(2L)
                .conCedula(1023009038L).conFechaCreacion(LocalDate.parse("2022-01-10"))
                .conFechaEntrega(LocalDate.parse("2022-01-24")).conEstado(2).build();

        //act - assert
        mockMvc.perform(put("/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor'='La solicitud fue actualizada con exito," +
                        " la persona queda suspendida hasta: "+fechaSuspension+ ", con un pago para efectuar de: "
                        +multa+"'}"));
    }

    public static LocalDate sumaFecha(int diasSuma, LocalDate fechaActual) {

        for (int i = 0; i < diasSuma; i++) {
            fechaActual = fechaActual.plusDays(1);
            if (fechaActual.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    fechaActual.getDayOfWeek() == DayOfWeek.SATURDAY) {
                diasSuma= diasSuma+1;
            }
        }

        return fechaActual;
    }

    public static Long calcularDiasPorFecha(LocalDate fechaPasada,LocalDate fechaActual){
        final DayOfWeek diaInicial = fechaPasada.getDayOfWeek();
        final DayOfWeek diaFinal = fechaActual.getDayOfWeek();

        Long diasTotales = DAYS.between(fechaPasada,fechaActual);
        Long finDeSemana = diasTotales -2 *((diaInicial.getValue())/7);

        return finDeSemana + (diaInicial == DayOfWeek.SUNDAY ? 1 : 0) + (diaFinal == DayOfWeek.SUNDAY ? 1 :0);
    }
}