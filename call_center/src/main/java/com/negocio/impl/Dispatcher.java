package com.negocio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.negocio.dto.EmpleadoDto;
import com.negocio.dto.LlamadaDto;
import com.negocio.dto.ParametroDto;
import com.negocio.dto.Recepcionresponse;
import com.negocio.dto.Respuesta;
import com.negocio.excepcion.RecepcionExcepcion;
import com.negocio.reglas.DispatchCall;
import com.negocio.util.Estados;
import com.negocio.util.Textos;

/**
 * @author HABUR
 *La responsabilidad de esta clase es administrar  las llamada y orquestar su gestion con asesores y parametros 
 */
public class Dispatcher {
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	@Autowired
	private AsesorDisponibleImpl asesorDisponible;
	@Autowired
	private GestionEmpleadoImpl gestionEmpleadoImpl;
	@Autowired
	private ParametroImpl parametroImpl;

	/**
	 * @param queue, recibe la lista de llamadas a procesar y validar
	 * @return Recepcionresponse, este  contiene las respuesta de la operacion, la lista de llamadas atendidas
	 */
	public Recepcionresponse atenderLLamadas(Queue<LlamadaDto> queue)  {
		Recepcionresponse recepcionresponse = new Recepcionresponse();
		Respuesta resp = null;
		List<EmpleadoDto> listaEmpleado = null;
		try {
			resp = validarLista( queue.stream().toArray());
			if (validarEstadoFalloTransaccion(resp)) {
				throw new RecepcionExcepcion(Textos.LISTA_VACIA_LLAMADAS.getTexto());
				
			}

			listaEmpleado = asesorDisponible.consultarEmpleados();
			resp=validarLista(listaEmpleado.stream().toArray());
			if(validarEstadoFalloTransaccion(resp)){
				resp.setObservacion(Textos.LISTA_VACIA_EMPLEADOS.getTexto());
				recepcionresponse.setRespuesta(resp);
				return recepcionresponse;
			}
			
			recepcionresponse.setListaLLamadas(mapeorespuesta(ejecutarProcesamento(queue, listaEmpleado)));
			recepcionresponse.setRespuesta(new Respuesta(Estados.CORRECTO.getEstado()));
		}catch (RecepcionExcepcion e) {
			resp.setObservacion(e.getMessage());
			recepcionresponse.setRespuesta(resp);
		}catch (Exception e) {
			LOGGER.info(e);
			resp.setEstadoTrasaccion(Estados.FALLO.getEstado());
			resp.setObservacion(e.getMessage());
			recepcionresponse.setRespuesta(resp);
		}

		return recepcionresponse;
	}

	
	/**
	 * @param listaResultados, LlamadaDto
	 * @return Lista de llamadas procesadas, sin el tipo Future
	 */
	private List<LlamadaDto> mapeorespuesta(List<Future<LlamadaDto>> listaResultados) {
		List<LlamadaDto> list = new ArrayList<>();

		listaResultados.forEach(temp -> {
			try {
				list.add(temp.get());
			} catch (Exception e) {
				LOGGER.error(e);
			}
		});
		return list;
	}

	/**
	 * @param queue
	 * @param listaEmpleado
	 * @return una lista de clase Future, con la la  cantidad de llamadas procesadas
	 * Metodo encarcadfo de ejecutar el procesamiento por un pool de hilos
	 */
	private List<Future<LlamadaDto>> ejecutarProcesamento(Queue<LlamadaDto> queue, List<EmpleadoDto> listaEmpleado) {
		ExecutorService executor = Executors.newFixedThreadPool(consultarCantidadHilos());
		List<Future<LlamadaDto>> listaResultados = new ArrayList<>();
		gestionEmpleadoImpl.gestionEmpleado(listaEmpleado);
		EmpleadoDto empleado;
		do {
			empleado = gestionEmpleadoImpl.validarReglasAsignacionAsesor();
			if (empleado != null) {
				Future<LlamadaDto> result = executor.submit(new DispatchCall(queue.poll(), empleado,listaEmpleado));
				listaResultados.add(result);
			}

		} while (!queue.isEmpty());

		executor.shutdown();
		while (!executor.isTerminated()) {
		};
		return listaResultados;
	}

	/**
	 * Consulta los parametros de hilos
	 * @return
	 */
	private int consultarCantidadHilos() {
		ParametroDto parametroHilos = parametroImpl.consultarEmpleados(Textos.LIMITE_HILOS.getTexto());
		return parametroHilos!=null?parametroHilos.getValor():1;
	}

	/**
	 * @param respuesta
	 * @return boolean
	 * Valida el estado de la transaccion ERROR
	 */
	private boolean validarEstadoFalloTransaccion(Respuesta respuesta) {
		return respuesta.getEstadoTrasaccion().equals(Estados.FALLO.getEstado());
	}

	/**
	 * @param objects
	 * @return un objeto respuesta
	 * @throws RecepcionExcepcion
	 * Valida si alguna de las listas contiene data
	 */
	private Respuesta validarLista(Object[] objects) throws RecepcionExcepcion {
		Respuesta respuesta = new Respuesta(Estados.CORRECTO.getEstado());

		if (objects == null || objects.length==0) {
			respuesta.setEstadoTrasaccion(Estados.FALLO.getEstado());
		}
		
		return respuesta;
	}
}
