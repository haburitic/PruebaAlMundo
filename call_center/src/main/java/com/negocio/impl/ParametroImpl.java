package com.negocio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.negocio.dto.ParametroDto;
import com.negocio.entity.Parametro;
import com.negocio.repository.ParametroDao;

/**
 * @author HABUR
 *La responsabilidad de esta clase es administrar los parametros que deben ir en BD
 */
@Service
public class ParametroImpl {
	   @Autowired
	    private ParametroDao dao;
	
	/**
	 * @param dato, String que determina el alias del parametro
	 * @return ParametroDto, con la informacion del parametro
	 */
	public ParametroDto consultarEmpleados(String dato) {
		return  realizarMaperoDeEntidad(dao.findByNombre(dato));
	}

	/**
	 * @param parametro
	 * @return Dto con parametro a utilizar
	 * Se realiza mapeo a DTO
	 */
	private ParametroDto realizarMaperoDeEntidad(Parametro parametro) {
		ParametroDto param =null;

		if(parametro!=null){
			param= new ParametroDto();
			param.setId(parametro.getId());
			param.setNombre(parametro.getNombre());
			param.setValor(parametro.getValor());

		}
		return param;
		
	}
	
}
