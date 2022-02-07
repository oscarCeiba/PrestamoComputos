package com.ceiba.prestamo.servicio;

import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import com.ceiba.suspension.modelo.entidad.Suspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import com.ceiba.usuario.puerto.repositorio.RepositorioUsuario;

import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.*;
import static com.ceiba.prestamo.modelo.enums.EnumEstadoSolicitud.*;
import static com.ceiba.prestamo.modelo.enums.EnumRolUsuario.*;

public class ServicioActualizarPrestamo {

    private static final String LA_SOLICITUD_NO_EXISTE_EN_EL_SISTEMA = "La solicitud no existe en el sistema";
    private static final String EL_ESTADO_ENVIADO_ES_DIFERENTE = "El estado enviado no corresponde al posible " +
            "a actualizar";
    private static final String LA_SOLICITUD_FUE_ACTUALIZADA_CON_EXITO = "La solicitud fue actualizada con exito";
    private static final String LA_SOLICITUD_FUE_ACTUALIZAD_Y_LA_FECHA_SUSPENSION_ES = "La solicitud fue actualizada " +
            "con exito, la persona queda suspendida hasta: ";
    private static final String CON_PAGO_DE_MULTA = ", con un pago para efectuar de: ";
    private static final String EL_USUARIO_TIENE_ROL_NO_PERMITIDO = "El usuario tiene un rol " +
            "diferente al permitido para la solicitud";


    private final RepositorioPrestamo repositorioPrestamo;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioSuspension repositorioSuspension;

    public ServicioActualizarPrestamo(RepositorioPrestamo repositorioPrestamo,
                                      RepositorioUsuario repositorioUsuario,
                                      RepositorioSuspension repositorioSuspension) {
        this.repositorioPrestamo = repositorioPrestamo;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioSuspension = repositorioSuspension;
    }

    public String ejecutar(Prestamo prestamo) {
        validarExistenciaPrevia(prestamo);
        validarEstadoPeticion(prestamo);
        String respuesta= validarFechaEntregaEquipoSuspension(prestamo);

        this.repositorioPrestamo.actualizar(prestamo);
        return respuesta;
    }

    private void validarEstadoPeticion(Prestamo prestamo){
        if(prestamo.getEstado() != INACTIVO.ordinal() && prestamo.getEstado() != SUSPENDIDO.ordinal()){
            throw new ExcepcionValorInvalido(EL_ESTADO_ENVIADO_ES_DIFERENTE);
        }
    }

    private void validarExistenciaPrevia(Prestamo prestamo) {
        boolean existe = this.repositorioPrestamo.existe(prestamo.getCedula());
        if(!existe) {
            throw new ExcepcionValorInvalido(LA_SOLICITUD_NO_EXISTE_EN_EL_SISTEMA);
        }
    }

    private String validarFechaEntregaEquipoSuspension(Prestamo prestamo){
        if(prestamo.getFechaEntrega().isBefore(LocalDate.now())){
           return calculoFechaMultaSuspension(prestamo);
        }else{
            return LA_SOLICITUD_FUE_ACTUALIZADA_CON_EXITO;
        }
    }

    private String calculoFechaMultaSuspension(Prestamo prestamo){
        int rol = obtenerRolUsuario(prestamo);
        Long multa = calcularMulta(prestamo,rol);
        LocalDate fechaFinSuspension = crearsolicitudSuspension(rol,multa,prestamo);
        return LA_SOLICITUD_FUE_ACTUALIZAD_Y_LA_FECHA_SUSPENSION_ES + fechaFinSuspension
                + CON_PAGO_DE_MULTA + multa;
    }

    private int obtenerRolUsuario(Prestamo prestamo){
        return this.repositorioUsuario.rolUsuario(prestamo.getCedula());
    }

    private LocalDate calcularFechaSuspension(int rol){
        LocalDate fechaFinSuspension = null;
        if(rol == ESTUDIANTES.getRol()){
            fechaFinSuspension = sumaFecha(20,LocalDate.now());
        }else if(rol == ADMINISTRATIVOS.getRol()){
            fechaFinSuspension = sumaFecha(30,LocalDate.now());
        }
        return fechaFinSuspension;
    }

    private Long calcularMulta(Prestamo prestamo,int rol){
        Long dias;
        if(rol == ESTUDIANTES.getRol()){
            dias = calcularDiasPorFecha(prestamo.getFechaEntrega(),LocalDate.now());
            return  dias * 55500;
        }else if(rol == ADMINISTRATIVOS.getRol()){
            dias = calcularDiasPorFecha(prestamo.getFechaEntrega(),LocalDate.now());
            return dias * 83500;
        }else{
            throw new ExcepcionValorInvalido(EL_USUARIO_TIENE_ROL_NO_PERMITIDO);
        }
    }

    private LocalDate crearsolicitudSuspension(int rol, Long multa,Prestamo prestamo){
        LocalDate fechaFinSuspension = calcularFechaSuspension(rol);
        Suspension suspension = new Suspension(
                prestamo.getId(),
                prestamo.getCedula(),
                LocalDate.now(),
                fechaFinSuspension,
                multa);
        this.repositorioSuspension.crear(suspension);
        return fechaFinSuspension;
    }
}
