package com.ceiba.prestamo.adaptador.dao;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.prestamo.modelo.dto.DtoPrestamo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MapeoPrestamo implements RowMapper<DtoPrestamo>, MapperResult {

    @Override
    public DtoPrestamo mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long id = resultSet.getLong("id");
        Long cedula = resultSet.getLong("cedula");
        String equipoComputo = resultSet.getString("equipo_computo");
        LocalDate fechaCreacion = extraerLocalDate(resultSet, "fecha_creacion");
        LocalDate fechaEntrega = extraerLocalDate(resultSet, "fecha_entrega");
        int estado = resultSet.getInt("estado");


        return new DtoPrestamo(id,cedula,equipoComputo,fechaCreacion,fechaEntrega,estado);
    }

}
