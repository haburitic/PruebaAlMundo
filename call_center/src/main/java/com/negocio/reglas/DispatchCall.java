package com.negocio.reglas;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import com.negocio.dto.EmpleadoDto;
import com.negocio.dto.LlamadaDto;
import com.negocio.util.Cantidades;
import com.negocio.util.Textos;

/**
 * @author HABUR
 *La responsabilidad de esta clase es recibir los datos de empleado, llamada y una lista,
 *Con esta información el asesor o empleado atiende en un tiempo aleatorio
 */
public class DispatchCall implements Callable<LlamadaDto> {

	private LlamadaDto llamada;
	private EmpleadoDto empleado;
	private List<EmpleadoDto> listaEmpleado;

	/**
	 * @param llamada
	 * @param empleado
	 * @param listaEmpleado
	 */
	public DispatchCall(LlamadaDto llamada, EmpleadoDto empleado, List<EmpleadoDto> listaEmpleado) {
		this.llamada = llamada;
		this.listaEmpleado = listaEmpleado;
		this.empleado = empleado;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 * Llamada por hilo, ejecuta el procesamiento
	 */
	@Override
	public LlamadaDto call() throws Exception {
		LlamadaDto respuesta = null;

		respuesta = processCommand(empleado);
		respuesta.setAsesor(empleado);
		System.out.println(Thread.currentThread().getName() + Textos.INFO_EMPLEADO.getTexto() + empleado.getNombre());
		respuesta.setFueProcesado(true);
		listaEmpleado.add(empleado);

		return respuesta;
	}

	/**
	 * @param empleado
	 * @return
	 * @throws InterruptedException
	 * Llama in metodo que trae un valor el cual es utilizado para hacer  un sleep y asigna un empleaado a una llamada 
	 */
	private LlamadaDto processCommand(EmpleadoDto empleado) throws InterruptedException {
		int duracionLlamada = asignarTiempoDeLlamada();
		Thread.sleep(duracionLlamada * Cantidades.VALOR_MULTIPLICADOR.getCantidad());

		llamada.setAsesor(empleado);
		llamada.setFueProcesado(true);
		llamada.setDuracionLlamada(duracionLlamada);
		return llamada;

	}

	/**
	 * @return
	 * Signa un tiempo determinado
	 */
	private int asignarTiempoDeLlamada() {
		return ThreadLocalRandom.current().nextInt(Cantidades.MINIMO_DE_UNA_LLAMADA.getCantidad(),
				Cantidades.MAXIMO_DE_UNA_LLAMADA.getCantidad());
	}
}