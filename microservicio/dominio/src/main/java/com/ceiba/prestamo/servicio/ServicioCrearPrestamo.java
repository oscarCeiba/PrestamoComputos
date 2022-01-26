package com.ceiba.prestamo.servicio;

import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import com.ceiba.suspension.modelo.dto.DtoSuspension;
import com.ceiba.suspension.puerto.dao.DaoSuspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import com.ceiba.usuario.puerto.repositorio.RepositorioUsuario;

import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.*;


public class ServicioCrearPrestamo {

    private static final String EL_USUARIO_NO_EXISTE_EN_EL_SISTEMA = "El usuario no existe en el sistema";
    private static final String EL_USUARIO_TIENE_SOLICITUD_ACTIVA = "El usuario tiene una solicitud activa";
    private static final String EL_USUARIO_TIENE_SOLICITUD_SUSPENDIDA = "El usuario tiene una solicitud suspendida" +
            " hasta la fecha: ";
    private static final String EL_USUARIO_TIENE_ROL_NO_PERMITIDO = "El usuario tiene un rol " +
            "diferente al permitido para la solicitud";
    private static final String LA_SOLICITUD_CREADA_TIENE_FECHA_DE_ENTREGA = "La solicitud fue realizada con exito, " +
            "la fecha maxima de entrega es: ";

    private final RepositorioPrestamo repositorioPrestamo;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioSuspension repositorioSuspension;
    private final DaoPrestamo daoPrestamo;
    private final DaoSuspension daoSuspension;

    public ServicioCrearPrestamo(RepositorioPrestamo repositorioPrestamo, RepositorioUsuario repositorioUsuario,
                                 DaoPrestamo daoPrestamo, DaoSuspension daoSuspension,
                                 RepositorioSuspension repositorioSuspension) {
        this.repositorioPrestamo = repositorioPrestamo;
        this.repositorioUsuario = repositorioUsuario;
        this.daoPrestamo = daoPrestamo;
        this.daoSuspension = daoSuspension;
        this.repositorioSuspension = repositorioSuspension;
    }

    public String ejecutar(Prestamo prestamo) {
        validarExistenciaUsuarioSolicitud(prestamo);
        validarSolicitudPrestamoExistenteEstado(prestamo);
        int rol = obtenerRolUsuario(prestamo);
        Prestamo prestamoCrear = new Prestamo(prestamo.getId(), prestamo.getCedula(), prestamo.getEquipoComputo(),
                prestamo.getFechaCreacion(),calcularFechaEntregaPorRol(prestamo,rol),1);
        this.repositorioPrestamo.crear(prestamoCrear);
        return LA_SOLICITUD_CREADA_TIENE_FECHA_DE_ENTREGA + prestamoCrear.getFechaEntrega();
    }

    private void validarExistenciaUsuarioSolicitud(Prestamo prestamo) {
        boolean existe = this.repositorioUsuario.existe(prestamo.getCedula());
        if(!existe) {
            throw new ExcepcionValorInvalido(EL_USUARIO_NO_EXISTE_EN_EL_SISTEMA);
        }
    }

    private void validarSolicitudPrestamoExistenteEstado(Prestamo prestamo){
       boolean existe = this.repositorioPrestamo.existe(prestamo.getCedula());
       if(existe){
          validarSolicitudPrestamoEstado(prestamo);
       }
    }

    private void validarSolicitudPrestamoEstado(Prestamo prestamo){
        DtoPrestamo prestamoEstado = this.daoPrestamo.solicitudCreada(prestamo.getCedula());
        if(prestamoEstado.getEstado() == 1){
            throw new ExcepcionValorInvalido(EL_USUARIO_TIENE_SOLICITUD_ACTIVA);
        }else{
            validarSuspension(prestamoEstado);
        }
    }

    private void validarSuspension(DtoPrestamo prestamo){
        DtoSuspension suspension = this.daoSuspension.solicitudCreada(prestamo.getCedula());
        validarMenor(suspension.getFechaFinSuspension(), LocalDate.now(),EL_USUARIO_TIENE_SOLICITUD_SUSPENDIDA +
                suspension.getFechaFinSuspension());
        this.repositorioPrestamo.actualizarEstado(prestamo.getId(),0);
        this.repositorioSuspension.eliminar(suspension.getId());
    }

    private int obtenerRolUsuario(Prestamo prestamo){
        return this.repositorioUsuario.rolUsuario(prestamo.getCedula());
    }

    private LocalDate calcularFechaEntregaPorRol(Prestamo prestamo,int rol){
        LocalDate fechaEntrega = null;
         if(rol == 1){
             fechaEntrega = sumaFecha(10,prestamo.getFechaCreacion());
         }else if(rol == 2){
             fechaEntrega = sumaFecha(15,prestamo.getFechaCreacion());
         }else{
             throw new ExcepcionValorInvalido(EL_USUARIO_TIENE_ROL_NO_PERMITIDO);
         }
         return fechaEntrega;
    }
}
