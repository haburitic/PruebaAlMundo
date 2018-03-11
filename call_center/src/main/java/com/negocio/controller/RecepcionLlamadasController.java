package com.negocio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.negocio.dto.Recepcionresponse;
import com.negocio.impl.RecepcionLlamadasBDImpl;

/**
 * La resposabilidad de esta clase es Gestionar las llamadas y empleados del  contact center, desde bd, 
 * @author HABUR
 *	
 */
@RestController
public class RecepcionLlamadasController {
	@Autowired
	private RecepcionLlamadasBDImpl recepcionLlamadas;


    /**
     * @return Retorna la lista de llamadas procesadas con datos de tiempo y asesor que la atendio
     * @param no recibe parametros ya que ejecuta con tada en bd
     */
    @RequestMapping(value = "/procesarLlamadas", method = RequestMethod.GET)
	public Recepcionresponse recibirLlamadasBd() {
		return recepcionLlamadas.gestionarLlamadas();
	}
    

 }
