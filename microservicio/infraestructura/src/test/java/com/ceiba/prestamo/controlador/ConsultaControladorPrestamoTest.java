package com.ceiba.prestamo.controlador;

import com.ceiba.ApplicationMock;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConsultaControladorPrestamo.class)
@ContextConfiguration(classes= ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ConsultaControladorPrestamoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deberia consultar una solicitud prestamo  por cedula")
    void deberiaConsultarUnaSolicitudConCedula() throws Exception{
        //arrange
        Long cedula = 1023009038L;
        //act - assert
        mockMvc.perform(get("/prestamo/{cedula}",cedula)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id'=2, 'cedula'=1023009038, 'equipoComputo'='Dell'," +
                        " 'fechaCreacion'='2022-01-10', 'fechaEntrega'='2022-01-24', 'estado'=1}"));

    }

    @Test
    @DisplayName("Deberia consultar una solicitud prestamo  por cedula que no existe")
    void deberiaConsultarUnaSolicitudConCedulaNoExiste() throws Exception{
        //arrange
        Long cedula = 1023009035L;
        //act - assert
        mockMvc.perform(get("/prestamo/{cedula}",cedula)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{" +
                        "    'nombreExcepcion': 'ExcepcionTecnica'," +
                        "    'mensaje': 'El usuario no tiene solicitudes de prestamos activas'}"));

    }


}