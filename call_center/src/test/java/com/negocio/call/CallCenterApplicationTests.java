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

import com.negocio.controller.RecepcionLlamadasController;
import com.negocio.dto.Recepcionresponse;
import com.negocio.entity.Empleado;
import com.negocio.entity.Llamada;
import com.negocio.entity.Parametro;
import com.negocio.impl.AsesorDisponibleImpl;
import com.negocio.impl.ConsultaLLamadasImpl;
import com.negocio.impl.GestionEmpleadoImpl;
import com.negocio.impl.Dispatcher;
import com.negocio.impl.ParametroImpl;
import com.negocio.impl.RecepcionLlamadasBDImpl;
import com.negocio.repository.EmpleadoDao;
import com.negocio.repository.LlamadasDao;
import com.negocio.repository.ParametroDao;
import com.negocio.util.CargosEmpleados;
import com.negocio.util.Estados;
import com.negocio.util.Textos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests {

	private 
	RecepcionLlamadasController recepcionLlamadasController = new RecepcionLlamadasController();
	
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
		ReflectionTestUtils.setField(recepcionLlamadasController, "recepcionLlamadas", recepcionLlamadas);
		ReflectionTestUtils.setField(recepcionLlamadas, "gestionEmpleadoImpl", gestionEmpleadoImpl);
		ReflectionTestUtils.setField(recepcionLlamadas, "consultaLLamadasImpl", consultaLLamadasImpl);
		ReflectionTestUtils.setField(consultaLLamadasImpl, "llamadasDao", llamadasDao);
		ReflectionTestUtils.setField(recepcionLlamadas, "asesorDisponible", asesorDisponible);
		ReflectionTestUtils.setField(recepcionLlamadas, "parametroImpl", parametroImpl);
		ReflectionTestUtils.setField(recepcionLlamadas, "gestionEmpleadoImpl", gestionEmpleadoImpl);

		Parametro parametro= new Parametro();
		parametro.setId(1l);
		parametro.setNombre("dato");
		parametro.setValor(10);
		Mockito.when(parametroDao.findByNombre(Mockito.anyString())).thenReturn(parametro);
	}


	@Test
	public void validarRercepcionLlamadasConListaVacia() throws InterruptedException {
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),  Estados.FALLO.getEstado());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaNull() throws InterruptedException {
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos1Empleado() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(1));

		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 1);
		 assertTrue(respuesta.getListaLLamadas().get(0).isFueProcesado());
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getCargo(),"OP");
		 assertEquals(respuesta.getListaLLamadas().get(0).getAsesor().getNombre(),"0");

	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos2Empleado2Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(2));

		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		
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
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(3));

		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		
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
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(true)).thenReturn(null);
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),Estados.FALLO.getEstado());
		 assertEquals(respuesta.getRespuesta().getObservacion(), Textos.LISTA_VACIA_LLAMADAS.getTexto());
	}
	
	@Test
	public void validarRercepcionLlamadasConListaElementosMinimos0Empleado1Llamadas() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(null);
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn(iterarListaLlamadas(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(),Estados.FALLO.getEstado());
		 assertEquals(respuesta.getRespuesta().getObservacion(), Textos.LISTA_VACIA_EMPLEADOS.getTexto());
	}
	
	@Test
	public void validarRercepcionLlamadasConLista5Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(5));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(5));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 5);

	}

	
	@Test
	public void validarRercepcionLlamadasConLista10Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(4));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(10));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 10);

	}
	
	@Test
	public void validarRercepcionLlamadasConLista20Elementos() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(10));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(20));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.CORRECTO.getEstado());
		 assertEquals(respuesta.getListaLLamadas().size(), 20);

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1ElementosErrorLlamada() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenThrow(new NullPointerException());
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1ElementosErrorEmpleado() throws InterruptedException {
		Mockito.when(empleadoDao.findAll()).thenThrow(new NullPointerException());
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1LlamadaVacio() throws InterruptedException {
		List<Llamada> dato= new ArrayList<>();
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(10));
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( dato);
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

	}
	
	@Test
	public void validarRercepcionLlamadasConLista1EmpleadoVacio() {
		List<Empleado> dato= new ArrayList<>();
		Mockito.when(empleadoDao.findAll()).thenReturn(dato);
		Mockito.when(llamadasDao.findByFueProcesadoOrderByIdDesc(false)).thenReturn( iterarListaLlamadas(1));
		 Recepcionresponse respuesta = recepcionLlamadasController.recibirLlamadasBd();
		 assertEquals(respuesta.getRespuesta().getEstadoTrasaccion(), Estados.FALLO.getEstado());

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
