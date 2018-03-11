package com.negocio.util;

public enum CargosEmpleados {
    OPERADOR("OP"),
    SUPERVISOR("SU"),
    DIRECTOR("DI");

    private String cargo;

    CargosEmpleados(String cargo) {
        this.cargo = cargo;
    }

    public String getOperador() {
        return cargo;
    }
}