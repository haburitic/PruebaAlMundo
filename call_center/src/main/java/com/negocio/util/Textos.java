package com.negocio.util;

public enum Textos {
    TIEMPO_PROCESADO("Tiempo total de procesamiento: "),
    SEGUNDOS(" Segundos"),
    LISTA_VACIA(" Lista Vacia "),
    LISTA_VACIA_EMPLEADOS("LISTA VACIA EMPLEADOS"),
    LISTA_VACIA_LLAMADAS("LISTA VACIA_LLAMADAS"),

    LIMITE_HILOS("LIMITE_HILOS"),
    INFO_EMPLEADO(" Empleado-> ");

    private String url;

    Textos(String url) {
        this.url = url;
    }

    public String getTexto() {
        return url;
    }
}