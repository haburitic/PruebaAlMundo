package com.negocio.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.negocio.entity.Parametro;

/**
 * @author HABUR
 * dao de Parametros

 */
@Transactional
@Repository
public interface ParametroDao extends CrudRepository<Parametro, Long> {
	/**
	 * @param procesado
	 * @return
	 * 
	 */
	public Parametro findByNombre(String procesado);

}