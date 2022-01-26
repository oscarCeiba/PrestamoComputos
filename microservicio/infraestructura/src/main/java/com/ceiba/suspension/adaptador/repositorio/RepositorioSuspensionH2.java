package com.ceiba.suspension.adaptador.repositorio;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.suspension.modelo.entidad.Suspension;
import com.ceiba.suspension.puerto.repositorio.RepositorioSuspension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioSuspensionH2 implements RepositorioSuspension {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="suspension", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="suspension", value="eliminar")
    private static String sqlEliminar;

    public RepositorioSuspensionH2(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Long crear(Suspension suspension) {
        return this.customNamedParameterJdbcTemplate.crear(suspension, sqlCrear);
    }

    @Override
    public void eliminar(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlEliminar, paramSource);
    }

}
