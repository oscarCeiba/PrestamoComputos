package com.ceiba.suspension.puerto.dao;

import com.ceiba.suspension.modelo.dto.DtoSuspension;


public interface DaoSuspension {

    /**
     * Permite retornar solicitud creada
     * @return de solicitud
     */
    DtoSuspension solicitudCreada(Long cedula);
}
