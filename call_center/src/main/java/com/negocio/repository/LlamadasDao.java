package com.negocio.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.negocio.entity.Llamada;



/**
 * @author HABUR
 * Dao de llamada
 */
@Transactional
@Repository
public interface LlamadasDao extends CrudRepository<Llamada, Long> {
	public List<Llamada> findByFueProcesadoOrderByIdDesc(boolean procesado);

}