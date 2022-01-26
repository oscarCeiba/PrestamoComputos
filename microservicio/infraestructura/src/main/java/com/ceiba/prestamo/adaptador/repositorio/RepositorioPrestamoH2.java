package com.ceiba.prestamo.adaptador.repositorio;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.prestamo.modelo.entidad.Prestamo;
import com.ceiba.prestamo.puerto.repositorio.RepositorioPrestamo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPrestamoH2 implements RepositorioPrestamo {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="prestamo", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="prestamo", value="actualizar")
    private static String sqlActualizar;

    @SqlStatement(namespace="prestamo", value="existe")
    private static String sqlExiste;

    @SqlStatement(namespace="prestamo", value="actualizarEstado")
    private static String sqlUpdateEstado;

    public RepositorioPrestamoH2(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Long crear(Prestamo prestamo) {
        return this.customNamedParameterJdbcTemplate.crear(prestamo, sqlCrear);
    }

    @Override
    public boolean existe(Long cedula) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("cedula", cedula);

        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlExiste,paramSource, Boolean.class);
    }

    @Override
    public void actualizar(Prestamo prestamo) {
        this.customNamedParameterJdbcTemplate.actualizar(prestamo, sqlActualizar);
    }

    @Override
    public void actualizarEstado(Long id,int estado){
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        paramSource.addValue("estado", estado);
        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlUpdateEstado,paramSource);
    }
}
