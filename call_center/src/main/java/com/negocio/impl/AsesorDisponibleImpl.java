package com.negocio.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.negocio.dto.EmpleadoDto;
import com.negocio.entity.Empleado;
import com.negocio.repository.EmpleadoDao;

/** 
 * la responsabilidad de eta clase es implementar los dao y relaizar las transformaciones para consultar un Empleado o asesor
 * @author HABUR
 *
 */
/**
 * @author HABUR
 *
 */
@Service
public class AsesorDisponibleImpl {
	   @Autowired
	    private EmpleadoDao empleadoDao;
	
	   
	/**
	 * @return retorna la lista de empleados en un dto
	 */
	public List<EmpleadoDto> consultarEmpleados() {
		return  realizarMaperoDeEntidad(empleadoDao.findAll());
	}

	/**
	 * @param list, recibe una lista de empleados en una entity Empleado
	 * @return una lista de empleados en un dto, para eevitar el sql injectionn
	 */
	private List<EmpleadoDto> realizarMaperoDeEntidad(List<Empleado> list) {
		List<EmpleadoDto> listaRetorno=Collections.synchronizedList(new ArrayList<>());
		if(list!=null){
			list.forEach(temp->{
				EmpleadoDto dto= new EmpleadoDto();
				dto.setActivo(temp.getActivo());
				dto.setCargo(temp.getCargo());
				dto.setNombre(temp.getNombre());
				listaRetorno.add(dto);
			});

		}

		return listaRetorno;
		
	}
	
}
