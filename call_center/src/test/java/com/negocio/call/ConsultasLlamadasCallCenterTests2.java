package com.negocio.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.negocio.controller.ConsultasLlamadasController;
import com.negocio.dto.LlamadaDto;
import com.negocio.entity.Empleado;
import com.negocio.entity.Llamada;
import com.negocio.impl.ConsultaLLamadasImpl;
import com.negocio.repository.LlamadasDao;
import com.negocio.util.CargosEmpleados;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsultasLlamadasCallCenterTests2 {

	private 
	ConsultasLlamadasController controller = new ConsultasLlamadasController();
	
	@Mock private LlamadasDao llamadasDao;
	@InjectMocks private ConsultaLLamadasImpl consultaLLamadasImpl= new ConsultaLLamadasImpl();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(controller, "consultaLLamadasImpl", consultaLLamadasImpl);
		ReflectionTestUtils.setField(consultaLLamadasImpl, "llamadasDao", llamadasDao);

	}


	@Test
	public void consultarListaVacia() {
		Mockito.when(llamadasDao.findAll()).thenReturn(null);
		 Iterator<LlamadaDto> respuesta = controller.consultarLlamadas();
		 assertFalse(respuesta.hasNext());
	}
	
	@Test
	public void consultarListaCon1Llamada() {
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn(iterarListaLlamadas(1));
		 Iterator<LlamadaDto> respuesta = controller.consultarLlamadas();
		 ArrayList<LlamadaDto> list = Lists.newArrayList(respuesta);
		 assertEquals(0, list.get(0).getNumeroLLamada());
	}
	
	
	
	public List<Llamada> iterarListaLlamadas(int cantidad){
		List<Llamada> cola = new ArrayList<>();
		for (int i = 0; i < cantidad; i++) {
			Llamada lista = new Llamada();
			lista.setNumeroLlamada(i);
			cola.add(lista);
		}
		
		return cola;
	}
	
	public List<Empleado> iterarListaEmpleados(int cantidad){
		 List<Empleado> listaEmpleados= Collections.synchronizedList(new ArrayList<>());
		iterartipoEmpleado(cantidad, listaEmpleados, CargosEmpleados.OPERADOR.getOperador());
		iterartipoEmpleado(cantidad, listaEmpleados, CargosEmpleados.SUPERVISOR.getOperador());
		iterartipoEmpleado(cantidad, listaEmpleados, CargosEmpleados.DIRECTOR.getOperador());

		return listaEmpleados;
	}


	private void iterartipoEmpleado(int cantidad, List<Empleado> listaEmpleados, String tipo) {
		for (int i = 0; i < cantidad; i++) {
			Empleado lista = new Empleado();
			lista.setActivo(true);
			lista.setCargo(tipo);
			lista.setNombre(String.valueOf(i));
			listaEmpleados.add(lista);
		}
	}
}
