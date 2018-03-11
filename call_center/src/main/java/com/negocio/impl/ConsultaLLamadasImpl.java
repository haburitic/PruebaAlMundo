package com.negocio.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.negocio.dto.LlamadaDto;
import com.negocio.entity.Llamada;
import com.negocio.repository.LlamadasDao;

/**
 * Esta clase tiene como objetivo gestionar las llamadas desde BD
 * @author HABUR
 *
 */
@Service
public class ConsultaLLamadasImpl{
	   @Autowired
	    private LlamadasDao llamadasDao;
	
	
	/**
	 * @return retorna una lista de llamadas  con la entity,despues de consultar la lista en bf
	 */
	public Queue<LlamadaDto> consultarLlamadas() {
		List<Llamada> var = llamadasDao.findByFueProcesadoOrderByIdDesc(false);
		return  realizarMaperoDeEntidad(var);
	}
	
	/**
	 * @param list recibe una lista de llamadas para ser guardadas en la bd
	 */
	public void guardarLlamadas(List<LlamadaDto> list){
		llamadasDao.saveAll(mapeoListaLlamada(list));
	}

	/**
	 * @param list
	 * @return una lista de llamadas de tipo List java, donde se mapea el asesor
	 */
	private List<Llamada> mapeoListaLlamada(List<LlamadaDto> list) {
		List<Llamada> lista= new ArrayList<>();
		list.forEach(temp->{
			Llamada llamada= new Llamada();
			llamada.setCargo(temp.getAsesor().getCargo());
			llamada.setNombreAsesor(temp.getAsesor().getCargo());
			llamada.setDuracionLlamada(temp.getDuracionLlamada());
			llamada.setFueProcesado(temp.isFueProcesado());
			llamada.setId(temp.getId());
			lista.add(llamada);
		});	
		return lista;
	}
	
	/**
	 * @param list, la lista de una entity LLamada
	 * @return se retorna dto, de una lista para evitar el sql injection
	 */
	private Queue<LlamadaDto> realizarMaperoDeEntidad(List<Llamada> list) {
		Queue<LlamadaDto> listaRetorno= new LinkedList<>();
		list.forEach(temp->{
			LlamadaDto dto= new LlamadaDto();
			dto.setDuracionLlamada(temp.getDuracionLlamada());
			dto.setFueProcesado(temp.getFueProcesado());
			dto.setNumeroLLamada(temp.getNumeroLlamada());
			dto.setId(temp.getId());
			listaRetorno.add(dto);
		});
		return listaRetorno;
	}

}
