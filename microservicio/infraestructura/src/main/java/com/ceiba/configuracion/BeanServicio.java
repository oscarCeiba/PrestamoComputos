package com.ceiba.configuracion;

import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import com.ceiba.prestamo.servicio.ServicioActualizarPrestamo;
import com.ceiba.prestamo.servicio.ServicioCrearPrestamo;
import com.ceiba.suspension.puerto.dao.DaoSuspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import com.ceiba.usuario.puerto.repositorio.RepositorioUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {


    @Bean
    public ServicioCrearPrestamo servicioCrearPrestamo(RepositorioPrestamo repositorioPrestamo,
                                                       RepositorioUsuario repositorioUsuario,
                                                       DaoPrestamo daoPrestamo, DaoSuspension daoSuspension,
                                                       RepositorioSuspension repositorioSuspension) {
        return new ServicioCrearPrestamo(repositorioPrestamo,
                repositorioUsuario, daoPrestamo, daoSuspension, repositorioSuspension);
    }

    @Bean
    public ServicioActualizarPrestamo servicioActualizarPrestamo(RepositorioPrestamo repositorioPrestamo,
                                                                 RepositorioUsuario repositorioUsuario,
                                                                 RepositorioSuspension repositorioSuspension) {
        return new ServicioActualizarPrestamo(repositorioPrestamo,
                repositorioUsuario, repositorioSuspension);
    }
	

}
