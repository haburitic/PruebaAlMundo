package com.negocio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Llamada implements Serializable {

	/**
	 * Entity encargado de realizar de representar la tabla de Llamadas
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private int numeroLlamada;
	
	@Column(nullable = true)
	private String cargo;
	
	@Column(nullable = true)
	private String nombreAsesor;
	
	@Column(nullable = false)
	private boolean fueProcesado;

	@Column(nullable = false)
	private int duracionLlamada;
	
	
	public boolean getFueProcesado() {
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

	public int getNumeroLlamada() {
		return numeroLlamada;
	}

	public void setNumeroLlamada(int numeroLLamada) {
		this.numeroLlamada = numeroLLamada;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getDuracionLlamada() {
		return duracionLlamada;
	}

	public void setDuracionLlamada(int duracionLlamada) {
		this.duracionLlamada = duracionLlamada;
	}
	
	public String getNombreAsesor() {
		return nombreAsesor;
	}

	public void setNombreAsesor(String nombreAsesor) {
		this.nombreAsesor = nombreAsesor;
	}
	
}

