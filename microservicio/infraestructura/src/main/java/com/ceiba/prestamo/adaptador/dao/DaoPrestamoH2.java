package com.ceiba.prestamo.adaptador.dao;

import com.ceiba.infraestructura.excepcion.ExcepcionTecnica;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import com.ceiba.prestamo.puerto.dao.DaoPrestamo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoPrestamoH2 implements DaoPrestamo {

    private static final String EL_USUARIO_NO_TIENE_SOLICITUDES = "El usuario no tiene solicitudes " +
            "de prestamos activas";

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="prestamo", value="solicitudCreada")
    private static String sqlSolicitudCreada;

    public DaoPrestamoH2(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    public DtoPrestamo solicitudCreada(Long cedula){
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("cedula", cedula);
        List<DtoPrestamo> prestamo = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .query(sqlSolicitudCreada,paramSource, new MapeoPrestamo());
        if(prestamo.isEmpty())
        {
            throw new ExcepcionTecnica(EL_USUARIO_NO_TIENE_SOLICITUDES);
        }
        return  prestamo.get(0);
    }
}
