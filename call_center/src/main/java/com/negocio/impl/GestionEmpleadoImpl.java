package com.negocio.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.negocio.dto.EmpleadoDto;
import com.negocio.util.CargosEmpleados;

@Service
public class GestionEmpleadoImpl{
	private List<EmpleadoDto> listaEmpleados;
	
	public void gestionEmpleado (List<EmpleadoDto> listaEmpleado){
		this.listaEmpleados=listaEmpleado;
	}
	

	public EmpleadoDto validarReglasAsignacionAsesor(){
		EmpleadoDto respuesta = validarOperador();
		if(respuesta==null){
			respuesta = validarSupervisora();
		}

		if(respuesta==null){
			respuesta = validarDirector();
		}
		return respuesta;
		
	}

	private EmpleadoDto validarOperador() {
		return buscarEmpleadoPorFiltro(CargosEmpleados.OPERADOR.getOperador());
	}
	
	private EmpleadoDto validarSupervisora() {
		return buscarEmpleadoPorFiltro(CargosEmpleados.SUPERVISOR.getOperador());
	}
	
	private EmpleadoDto validarDirector() {
		return buscarEmpleadoPorFiltro(CargosEmpleados.DIRECTOR.getOperador());
	}
	
	private EmpleadoDto buscarEmpleadoPorFiltro(String tipoEmpleado) {
		EmpleadoDto empleadoRetorno = null;
		for (int i = 0; i < listaEmpleados.size(); i++) {
			if(listaEmpleados.get(i).getCargo().equals(tipoEmpleado)){
				empleadoRetorno=listaEmpleados.get(i);
				listaEmpleados.remove(i);
				break;
			}
		}
		return empleadoRetorno;
	}
	
}
