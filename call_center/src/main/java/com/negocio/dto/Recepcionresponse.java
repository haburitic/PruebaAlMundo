package com.negocio.dto;

import java.util.List;

public class Recepcionresponse {
	
	private Respuesta respuesta;
	
	private List<LlamadaDto> listaLLamadas;

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

	public List<LlamadaDto> getListaLLamadas() {
		return listaLLamadas;
	}

	public void setListaLLamadas(List<LlamadaDto> respuesta2) {
		this.listaLLamadas = respuesta2;
	}

}
