package com.negocio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.negocio.dto.EmpleadoDto;
import com.negocio.impl.AsesorDisponibleImpl;

/**
 * La responsabilidad de la clase es gestionar consultas con respesto a los emplados del call center
 * @author HABUR
 *
 */
@RestController
public class ConsultasEmpleadosController {
	
	@Autowired private AsesorDisponibleImpl asesorDisponibleImpl;
	

     /**
     * @return Este metdo retorna una lista de empleados
     * @param No recibe parametros ya que muestra toda la lista
     */
    @RequestMapping(value = "/ConsultarEmpleados", method = RequestMethod.GET)
	public List<EmpleadoDto> consultarEmpleados() {
		return asesorDisponibleImpl.consultarEmpleados();
	}
}
