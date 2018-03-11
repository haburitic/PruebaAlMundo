package com.negocio.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.negocio.dto.LlamadaDto;
import com.negocio.dto.Recepcionresponse;
import com.negocio.dto.Respuesta;
import com.negocio.util.Cantidades;
import com.negocio.util.Estados;
import com.negocio.util.Textos;

@Service
public class RecepcionLlamadasListaImpl  extends Dispatcher{

	private static final Logger LOGGER = Logger.getLogger(RecepcionLlamadasListaImpl.class.getName());

	public Recepcionresponse gestionarLlamadas(List<LlamadaDto> Llamadas) {
		long init = System.currentTimeMillis();
		Recepcionresponse recepcionresponse = new Recepcionresponse();
		try {
			Queue<LlamadaDto> queue = new LinkedList<>(Llamadas);
			recepcionresponse = super.atenderLLamadas(queue);
			LOGGER.info(Textos.TIEMPO_PROCESADO.getTexto() + (System.currentTimeMillis() - init) / Cantidades.VALOR_MULTIPLICADOR.getCantidad() + Textos.SEGUNDOS.getTexto());
		} catch (Exception e) {
			LOGGER.info(e);
			Respuesta resp = new Respuesta(Estados.FALLO.getEstado());
			resp.setObservacion(e.getMessage());
			recepcionresponse.setRespuesta(resp);	
		}


		return recepcionresponse;
	}
}
