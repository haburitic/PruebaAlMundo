package com.negocio.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.negocio.controller.RecepcionLlamadasListaController;
import com.negocio.dto.LlamadaDto;
import com.negocio.dto.Recepcionresponse;
import com.negocio.entity.Empleado;
import com.negocio.entity.Parametro;
import com.negocio.impl.AsesorDisponibleImpl;
import com.negocio.impl.ConsultaLLamadasImpl;
import com.negocio.impl.GestionEmpleadoImpl;
import com.negocio.impl.Dispatcher;
import com.negocio.impl.ParametroImpl;
import com.negocio.impl.RecepcionLlamadasBDImpl;
import com.negocio.impl.RecepcionLlamadasListaImpl;
import com.negocio.repository.EmpleadoDao;
import com.negocio.repository.LlamadasDao;
import com.negocio.repository.ParametroDao;
import com.negocio.util.CargosEmpleados;
import com.negocio.util.Estados;
import com.negocio.util.Textos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumoLlamadasListaTests {

	private 
	RecepcionLlamadasListaController recepcionLlamadasController = new RecepcionLlamadasListaController();
	@InjectMocks private RecepcionLlamadasListaImpl recepcionLlamadasListaImpl= new RecepcionLlamadasListaImpl();

	@Mock private LlamadasDao llamadasDao;
	@Mock private EmpleadoDao empleadoDao;
	@Mock private ParametroDao parametroDao;
	@InjectMocks Dispatcher gestionLlamadas= new Dispatcher();
	@InjectMocks private GestionEmpleadoImpl gestionEmpleadoImpl= new GestionEmpleadoImpl();
	@InjectMocks private ConsultaLLamadasImpl consultaLLamadasImpl= new ConsultaLLamadasImpl();

	
	@InjectMocks private AsesorDisponibleImpl asesorDisponible= new AsesorDisponibleImpl();

	@InjectMocks private RecepcionLlamadasBDImpl recepcionLlamadas = new RecepcionLlamadasBDImpl();
	@InjectMocks private ParametroImpl parametroImpl= new ParametroImpl();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(recepcionLlamadasController, "recepcionLlamadas", recepcionLlamadasListaImpl);
		ReflectionTestUtils.setField(recepcionLlamadasListaImpl, "gestionEmpleadoImpl", gestionEmpleadoImpl);
		ReflectionTestUtils.setField(recepcionLlamadasListaImpl, "asesorDisponible", asesorDisponible);
		ReflectionTestUtils.setField(recepcionLlamadasListaImpl, "parametroImpl", parametroImpl);
		ReflectionTestUtils.setField(recepcionLlamadasListaImpl, "gestionEmpleadoImpl", gestionEmpleadoImpl);

		Parametro parametro= new Parametro();
		parametro.setId(1l);
		parametro.setNombre("dato");
		parametro.setValor(10);
		Mockito.when(parametroDao.findByNombre(Mockito.anyString())).thenReturn(parametro);
	}


	@Test
	public void validarRercepcionLlamadasConListaVaciaParaLista()  {
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(null);
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),  Estados.FALLO.getEstado());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaVaciaParaParametroVacio() throws InterruptedException {
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(new ArrayList<>());
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos1Llamada() {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));

		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(1));
		
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 1);
		 assertTrue(respuesta.getListaLLamadas().get(0).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getCargo(),"OP");
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getNombre(),"0");

	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos2Empleado2Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(2));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 2);
		 assertTrue(respuesta.getListaLLamadas().get(0).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getCargo(),"OP");
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getNombre(),"0");
		 assertTrue(respuesta.getListaLLamadas().get(1).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(1).getAsesor().getCargo(),"SU");
		 assertEquals(respuesta.getListaLLamadas().get(1).getAsesor().getNombre(),"0");
	}

	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos3Empleado3Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(3));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 3);
		 assertTrue(respuesta.getListaLLamadas().get(0).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getCargo(),"OP");
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getNombre(),"0");
		 assertTrue(respuesta.getListaLLamadas().get(1).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(1).getAsesor().getCargo(),"SU");
		 assertEquals(respuesta.getListaLLamadas().get(1).getAsesor().getNombre(),"0");
		 assertTrue(respuesta.getListaLLamadas().get(2).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(2).getAsesor().getCargo(),"DI");
		 assertEquals(respuesta.getListaLLamadas().get(2).getAsesor().getNombre(),"0");
	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos1Empleado0Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(null);
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),Estados.FALLO.getEstado());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos0Empleado1Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(null);
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(1));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),Estados.FALLO.getEstado());
		 assertEquals(respuesta.getRespuesta().getObservacion(), Textos.LISTA_VACIA_EMPLEADOS.getTexto());
	}
	
	@Test
	public void validarRercepcionLlamadasConLista5Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(5));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(5));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 5);

	}

	
	@Test
	public void validarRercepcionLlamadasConLista10Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(4));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(10));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 10);

	}
	
	@Test
	public void validarRercepcionLlamadasConLista20Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(10));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(20));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 20);

	}
	

	@Test
	public void validarRercepcionLlamadasConLista1ElementosErrorEmpleado() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenThrow(new NullPointerException());
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(1));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1LlamadaVacio() throws InterruptedException {
		List<LlamadaDto> dato= new ArrayList<>();
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(dato);
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1EmpleadoVacio() throws InterruptedException {
		List<Empleado> dato= new ArrayList<>();
		Mockito.when(empleadoDao.findAll()).thenReturn(dato);
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasLista(iterarListaLlamadasDto(1));
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
		
	public List<LlamadaDto> iterarListaLlamadasDto(int cantidad){
		List<LlamadaDto> cola = new ArrayList<>();
		for (int i = 0; i < cantidad; i++) {
			LlamadaDto lista = new LlamadaDto();
			lista.setNumeroLLamada(i);
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
