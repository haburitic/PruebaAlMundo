package com.negocio.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.negocio.dto.LlamadaDto;
import com.negocio.impl.ConsultaLLamadasImpl;

/**
 * la unica responsabilidad de la clase es gestionar las consultas de las  llamadas
 * @author haburitic@gmail.com
 *
 */
@RestController
public class ConsultasLlamadasController {
	
	@Autowired private ConsultaLLamadasImpl consultaLLamadasImpl;
	

     /**
     * @return retorna la lista de llamadas activas
     * @param No recibe parametros, ya que retorna todas las llamadas activas
     */
    @RequestMapping(value = "/ConsultarLlamadas", method = RequestMethod.GET)
	public Iterator<LlamadaDto> consultarLlamadas() {
		return consultaLLamadasImpl.consultarLlamadas().iterator();
	}
}
