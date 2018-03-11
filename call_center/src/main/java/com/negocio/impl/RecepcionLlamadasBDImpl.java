package com.negocio.impl;

import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.negocio.dto.LlamadaDto;
import com.negocio.dto.Recepcionresponse;
import com.negocio.dto.Respuesta;
import com.negocio.util.Cantidades;
import com.negocio.util.Estados;
import com.negocio.util.Textos;

/**
 * @author HABUR
 *El onjetivo de la calse es gestionar Las llamadas desde la bd, para un contactcenter
 */
@Service
public class RecepcionLlamadasBDImpl  extends Dispatcher{

	private static final Logger LOGGER = Logger.getLogger(RecepcionLlamadasBDImpl.class.getName());

	@Autowired private ConsultaLLamadasImpl consultaLLamadasImpl;

	/**
	 * @return Recepcionresponse
	 * Este metodo gestiona las llamadas a otras implementaciones como consultar 
	 * llamadas y llamar a gestion llamadas para gestionar el  contact center desde bd
	 */
	public Recepcionresponse gestionarLlamadas() {
		Recepcionresponse recepcionresponse = new Recepcionresponse();

		try {
			long init = System.currentTimeMillis();

			Queue<LlamadaDto> queue = consultaLLamadasImpl.consultarLlamadas();
			recepcionresponse = super.atenderLLamadas(queue);
			guardadoLlamadasProcesadas(recepcionresponse);
			LOGGER.info(Textos.TIEMPO_PROCESADO.getTexto() + (System.currentTimeMillis() - init) / Cantidades.VALOR_MULTIPLICADOR.getCantidad() + Textos.SEGUNDOS.getTexto());

		} catch (Exception e) {
			LOGGER.info(e);
			Respuesta resp = new Respuesta(Estados.FALLO.getEstado());
			resp.setObservacion(e.getMessage());
			recepcionresponse.setRespuesta(resp);		
		}
		
		return recepcionresponse;
	}


	/**
	 * @param recepcionresponse
	 * valida la lista de llamadas procesadas para ser guardas
	 */
	private void guardadoLlamadasProcesadas(Recepcionresponse recepcionresponse) {
		if(recepcionresponse.getListaLLamadas()!=null ){
			guardarLlamadasProcesadas(recepcionresponse.getListaLLamadas());

		}
	}

		
	/**
	 * @param list
	 * Guarda la lista de llamadas procesadas
	 */
	private void guardarLlamadasProcesadas(List<LlamadaDto> list){
		consultaLLamadasImpl.guardarLlamadas(list);
	}

	
}
