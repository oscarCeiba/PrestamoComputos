package com.ceiba.suspension.puerto.repositorio;

import com.ceiba.suspension.modelo.entidad.Suspension;

public interface RepositorioSuspension {
    /**
     * Permite crear una suspension
     * @param suspension
     * @return el id generado
     */
    Long crear(Suspension suspension);


    /**
     * Permite eliminar una suspension
     * @param id
     */
    void eliminar(Long id);


}
