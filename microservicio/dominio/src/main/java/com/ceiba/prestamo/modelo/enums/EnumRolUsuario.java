package com.ceiba.prestamo.modelo.enums;

import lombok.Getter;

@Getter
public enum EnumRolUsuario {
    ESTUDIANTES(1),
    ADMINISTRATIVOS(2);

    private int rol;

    EnumRolUsuario(int rol) {
        this.rol = rol;
    }
}
