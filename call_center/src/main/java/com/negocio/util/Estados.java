package com.negocio.util;

public enum Estados {
    CORRECTO("OK"),
    FALLO("ER");

    private String url;

    Estados(String url) {
        this.url = url;
    }

    public String getEstado() {
        return url;
    }
}