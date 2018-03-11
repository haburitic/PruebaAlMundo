package com.negocio.util;

public enum Cantidades {
    VALOR_MULTIPLICADOR(1000),
    MINIMO_DE_UNA_LLAMADA(5),
    MAXIMO_DE_UNA_LLAMADA(10);

    private int cantidad;

    Cantidades(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
}