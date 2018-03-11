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

import com.negocio.controller.ConsultasEmpleadosController;
import com.negocio.dto.EmpleadoDto;
import com.negocio.entity.Empleado;
import com.negocio.impl.AsesorDisponibleImpl;
import com.negocio.repository.EmpleadoDao;
import com.negocio.util.CargosEmpleados;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsultasEmpleadosCallCenterTests {

	private 
	ConsultasEmpleadosController consultasEmpleadosController = new ConsultasEmpleadosController();
	
	@Mock private EmpleadoDao empleadoDao;
	@InjectMocks  private AsesorDisponibleImpl asesorDisponibleImpl= new AsesorDisponibleImpl();


	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(consultasEmpleadosController, "asesorDisponibleImpl", asesorDisponibleImpl);
		ReflectionTestUtils.setField(asesorDisponibleImpl, "empleadoDao", empleadoDao);

	}


	@Test
	public void consultarListaVacia() {
		Mockito.when(empleadoDao.findAll()).thenReturn(null);
		 List<EmpleadoDto> respuesta = consultasEmpleadosController.consultarEmpleados();
		 assertTrue(respuesta.isEmpty());
	}
	
	@Test
	public void consultarListaCon1Empleado() {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 List<EmpleadoDto> respuesta = consultasEmpleadosController.consultarEmpleados();
		 assertEquals("0", respuesta.get(0).getNombre());
		 assertEquals("OP", respuesta.get(0).getCargo());
		 assertEquals(true, respuesta.get(0).isActivo());

	}
	
	@Test
	public void consultarListaCon2Empleado() {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 List<EmpleadoDto> respuesta = consultasEmpleadosController.consultarEmpleados();
		 assertEquals("0", respuesta.get(0).getNombre());
		 assertEquals("OP", respuesta.get(0).getCargo());
		 assertEquals(true, respuesta.get(0).isActivo());
		 assertEquals("0", respuesta.get(1).getNombre());
		 assertEquals("SU", respuesta.get(1).getCargo());
		 assertEquals(true, respuesta.get(1).isActivo());

	}
	
	@Test
	public void consultarListaCon3Emplados() {
		Mockito.when(empleadoDao.findAll()).thenReturn(iterarListaEmpleados(1));
		 List<EmpleadoDto> respuesta = consultasEmpleadosController.consultarEmpleados();
		 assertEquals("0", respuesta.get(0).getNombre());
		 assertEquals("OP", respuesta.get(0).getCargo());
		 assertEquals(true, respuesta.get(0).isActivo());
		 assertEquals("0", respuesta.get(1).getNombre());
		 assertEquals("SU", respuesta.get(1).getCargo());
		 assertEquals(true, respuesta.get(1).isActivo());
		 assertEquals("0", respuesta.get(2).getNombre());
		 assertEquals("DI", respuesta.get(2).getCargo());
		 assertEquals(true, respuesta.get(2).isActivo());

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
