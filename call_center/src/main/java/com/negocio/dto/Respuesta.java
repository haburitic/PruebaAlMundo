package com.negocio.dto;


public class Respuesta {
	private String estadoTrasaccion;
	private String observacion;

	public Respuesta(String estado){
		this.estadoTrasaccion=estado;
	}
	public String getEstadoTrasaccion() {
		return estadoTrasaccion;
	}

	public void setEstadoTrasaccion(String estadoTrasaccion) {
		this.estadoTrasaccion = estadoTrasaccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
