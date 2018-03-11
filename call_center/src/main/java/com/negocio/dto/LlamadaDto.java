package com.negocio.dto;


public class LlamadaDto {
	private Long id;
	private int numeroLLamada;
	private EmpleadoDto asesor;
	private int duracionLlamada;

	private boolean fueProcesado;
	
	public int getNumeroLLamada() {
		return numeroLLamada;
	}
	public void setNumeroLLamada(int numeroLLamada) {
		this.numeroLLamada = numeroLLamada;
	}
	public EmpleadoDto getAsesor() {
		return asesor;
	}
	public void setAsesor(EmpleadoDto asesor) {
		this.asesor = asesor;
	}

	public int getDuracionLlamada() {
		return duracionLlamada;
	}
	public void setDuracionLlamada(int duracionLlamada) {
		this.duracionLlamada = duracionLlamada;
	}
	public boolean isFueProcesado() {
		return fueProcesado;
	}
	public void setFueProcesado(boolean fueProcesado) {
		this.fueProcesado = fueProcesado;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
