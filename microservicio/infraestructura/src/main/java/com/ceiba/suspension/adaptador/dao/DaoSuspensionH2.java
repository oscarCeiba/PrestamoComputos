package com.ceiba.suspension.adaptador.dao;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.suspension.modelo.dto.DtoSuspension;
import com.ceiba.suspension.puerto.dao.DaoSuspension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoSuspensionH2 implements DaoSuspension {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="suspension", value="solicitudCreada")
    private static String sqlSolicitudCreada;

    public DaoSuspensionH2(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    public DtoSuspension solicitudCreada(Long cedula){
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("cedula", cedula);
        List<DtoSuspension> suspension = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlSolicitudCreada,paramSource, new MapeoSuspension());
        return suspension.get(0);
    }
}
