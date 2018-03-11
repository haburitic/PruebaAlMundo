package com.negocio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.negocio.dto.LlamadaDto;
import com.negocio.dto.Recepcionresponse;
import com.negocio.impl.RecepcionLlamadasListaImpl;

/** la responsabilidad de esta clase es gestionar la rececion 
 * de llamadas para ser procesadas y atendidas por un asesor de un  call center
 * @author HABUR
 * 
 */
@RestController
public class RecepcionLlamadasListaController {
	@Autowired
	private RecepcionLlamadasListaImpl recepcionLlamadas;
	
    /**
     * @param lista, de llamadas que deben ser atendidas
     * @return una lista de llamadas, con datos de la llamada y el asesor que la atendio
     */
    @RequestMapping(value = "/procesarLlamadas", method = RequestMethod.POST)
	public Recepcionresponse recibirLlamadasLista(@RequestBody List<LlamadaDto> lista) {
		return recepcionLlamadas.gestionarLlamadas(lista);
	}
    
      
 }
