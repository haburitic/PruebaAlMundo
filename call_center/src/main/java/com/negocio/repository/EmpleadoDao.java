package com.negocio.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.negocio.entity.Empleado;


/**
 * @author HABUR
 * Dao de empleados
 */
@Transactional
public interface EmpleadoDao extends CrudRepository<Empleado, Long> {
	@Override
	List<Empleado> findAll();
}