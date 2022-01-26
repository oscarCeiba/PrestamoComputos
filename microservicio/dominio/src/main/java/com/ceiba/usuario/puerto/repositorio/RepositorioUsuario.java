package com.ceiba.usuario.puerto.repositorio;

public interface RepositorioUsuario {

    /**
     * Permite validar si existe un usuario por cedula
     * @param cedula
     * @return si existe o no
     */
    boolean existe(Long cedula);

    /**
     * Permite obtener el rol del usuario
     * @param cedula
     * @return
     */
    int rolUsuario(Long cedula);

}
