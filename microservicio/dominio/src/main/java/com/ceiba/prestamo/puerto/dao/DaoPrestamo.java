package com.ceiba.prestamo.puerto.dao;

import com.ceiba.prestamo.modelo.dto.DtoPrestamo;


public interface DaoPrestamo {

    /**
     * Permite retornar solicitud creada
     * @return de solicitud
     */
    DtoPrestamo solicitudCreada(Long cedula);
}
