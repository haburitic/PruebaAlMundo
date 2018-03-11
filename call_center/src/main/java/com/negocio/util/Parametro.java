package com.negocio.util;

public enum Parametro {

    LIMITE_HILOS("LIMITE_HILOS");

    private String url;

    Parametro(String url) {
        this.url = url;
    }

    public String getTexto() {
        return url;
    }
}