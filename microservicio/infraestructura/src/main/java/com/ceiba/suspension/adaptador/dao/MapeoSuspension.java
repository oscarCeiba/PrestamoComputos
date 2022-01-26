package com.ceiba.suspension.adaptador.dao;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.suspension.modelo.dto.DtoSuspension;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MapeoSuspension implements RowMapper<DtoSuspension>, MapperResult {

    @Override
    public DtoSuspension mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long id = resultSet.getLong("id");
        Long cedula = resultSet.getLong("cedula");
        LocalDate fechaSuspension = extraerLocalDate(resultSet, "fecha_suspension");
        LocalDate fechaFinSuspension = extraerLocalDate(resultSet, "fecha_fin_suspension");
        Long pagoRealizado = resultSet.getLong("pago_realizado");

        return new DtoSuspension(id,cedula,fechaSuspension,fechaFinSuspension,pagoRealizado);
    }

}
