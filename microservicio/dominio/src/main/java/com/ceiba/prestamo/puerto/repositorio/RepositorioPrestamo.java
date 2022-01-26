package com.ceiba.prestamo.puerto.repositorio;

import com.ceiba.prestamo.modelo.entidad.Prestamo;

public interface RepositorioPrestamo {
    /**
     * Permite crear un prestamo
     * @param prestamo
     * @return el id generado
     */
    Long crear(Prestamo prestamo);

    /**
     * Permite actualizar un prestamo
     * @param prestamo
     */
    void actualizar(Prestamo prestamo);

    /**
     * Permite validar si existe un prestamo con cedula
     * @param cedula
     * @return si existe o no
     */
    boolean existe(Long cedula);

    /**
     * permite actualizar el estado de la solicitud
     * @param id
     * @param estado
     */
     void actualizarEstado(Long id,int estado);

}
